package com.wad.tBook.login

import android.annotation.TargetApi
import android.app.KeyguardManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import android.os.Bundle
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.wad.tBook.MainActivity
import com.wad.tBook.MyApplication
import com.wad.tBook.R
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey



class LoginMainActivity : AppCompatActivity() {
    var keyStore: KeyStore? = null

    private val CUSTOM_PREF_NAME = "User_data"
    private val sharedPreferences: SharedPreferences = MyApplication.context.getSharedPreferences(CUSTOM_PREF_NAME, Context.MODE_PRIVATE)
    private val conditionNumber = sharedPreferences.getInt("fingerprintpassword",0)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loginwith_fingerprint)
        if (conditionNumber == 0){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        else{
            if (supportFingerprint()) {
                initKey()
                initCipher()
            }
        }
    }

    fun supportFingerprint(): Boolean {
        if (Build.VERSION.SDK_INT < 23) {
            Toast.makeText(this, "您的系统版本过低，不支持指纹功能", Toast.LENGTH_SHORT).show()
            return false
        } else {
            val keyguardManager = getSystemService(
                KeyguardManager::class.java
            )
            val fingerprintManager = getSystemService(
                FingerprintManager::class.java
            )
            if (!fingerprintManager.isHardwareDetected) {
                Toast.makeText(this, "您的手机不支持指纹功能", Toast.LENGTH_SHORT).show()
                return false
            } else if (!keyguardManager.isKeyguardSecure) {
                Toast.makeText(this, "您还未设置锁屏，请先设置锁屏并添加一个指纹", Toast.LENGTH_SHORT).show()
                return false
            } else if (!fingerprintManager.hasEnrolledFingerprints()) {
                Toast.makeText(this, "您至少需要在系统设置中添加一个指纹", Toast.LENGTH_SHORT).show()
                return false
            }
        }
        return true
    }

    @TargetApi(23)
    private fun initKey() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore")
            keyStore!!.load(null)
            val keyGenerator = KeyGenerator.getInstance(
                KeyProperties.KEY_ALGORITHM_AES,
                "AndroidKeyStore"
            )
            val builder = KeyGenParameterSpec.Builder(
                DEFAULT_KEY_NAME,
                KeyProperties.PURPOSE_ENCRYPT or
                        KeyProperties.PURPOSE_DECRYPT
            )
                .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                .setUserAuthenticationRequired(true)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
            keyGenerator.init(builder.build())
            keyGenerator.generateKey()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    @TargetApi(23)
    private fun initCipher() {
        try {
            val key = keyStore!!.getKey(
                DEFAULT_KEY_NAME,
                null
            ) as SecretKey
            val cipher = Cipher.getInstance(
                KeyProperties.KEY_ALGORITHM_AES + "/"
                        + KeyProperties.BLOCK_MODE_CBC + "/"
                        + KeyProperties.ENCRYPTION_PADDING_PKCS7
            )
            cipher.init(Cipher.ENCRYPT_MODE, key)
            showFingerPrintDialog(cipher)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    private fun showFingerPrintDialog(cipher: Cipher) {
        val fragment = FingerprintDialogFragment()
        fragment.setCipher(cipher)
        fragment.show(fragmentManager, "fingerprint")
    }

    fun onAuthenticated() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object {
        private const val DEFAULT_KEY_NAME = "default_key"
    }
}