package com.kakapo.workmanagerblurephoto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kakapo.workmanagerblurephoto.databinding.ActivityBlurBinding

class BlurActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityBlurBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityBlurBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }
}