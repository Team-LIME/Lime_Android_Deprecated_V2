package kr.hs.dgsw.avocatalk.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import kr.hs.dgsw.avocatalk.BR
import kr.hs.dgsw.avocatalk.R
import kr.hs.dgsw.avocatalk.base.BaseActivity
import kr.hs.dgsw.avocatalk.data.exception.TokenException
import kr.hs.dgsw.avocatalk.data.widget.GlobalValue
import kr.hs.dgsw.avocatalk.databinding.ActivityRegisterBinding
import kr.hs.dgsw.avocatalk.domain.request.LoginRequest
import kr.hs.dgsw.avocatalk.domain.request.RegisterRequest
import kr.hs.dgsw.avocatalk.eventobserver.activity.RegisterEventObserver
import kr.hs.dgsw.avocatalk.view.dialog.MessageDialog
import kr.hs.dgsw.avocatalk.viewmodel.AuthViewModel
import kr.hs.dgsw.avocatalk.viewmodelfactory.AuthViewModelFactory
import kr.hs.dgsw.avocatalk.widget.SimpleTextWatcher
import javax.inject.Inject

class RegisterActivity : BaseActivity<ActivityRegisterBinding>() {

    @Inject
    lateinit var mAuthViewModelFactory: AuthViewModelFactory

    private val mAuthViewModel: AuthViewModel
        get() = getViewModel(mAuthViewModelFactory)

    override fun setDataBinding() {
        super.setDataBinding()
        initBindingData(BR.globalValue, GlobalValue)
        initBindingData(BR.eventObserver, object :
            RegisterEventObserver {
            override fun onClickRegisterBtn() {

                mBinding.inputLayoutName.error  = null
                mBinding.inputLayoutEmail.error  = null
                mBinding.inputLayoutPassword.error  = null
                mBinding.inputLayoutCheckPassword.error  = null
                mBinding.useAgreementCheckBox.error  = null


                when {
                    mBinding.name.isNullOrBlank() -> {
                        mBinding.inputLayoutName.error =
                            getString(R.string.error_msg_empty_name)
                        mBinding.inputLayoutName.requestFocus()
                    }

                    mBinding.email.isNullOrBlank() -> {
                        mBinding.inputLayoutEmail.error =
                            getString(R.string.error_msg_empty_email)
                        mBinding.inputLayoutEmail.requestFocus()
                    }

                    mBinding.pw.isNullOrBlank() -> {
                        mBinding.inputLayoutPassword.error =
                            getString(R.string.error_msg_empty_pw)
                        mBinding.inputLayoutPassword.requestFocus()
                    }

                    mBinding.checkPw.isNullOrBlank() -> {
                        mBinding.inputLayoutCheckPassword.error =
                            getString(R.string.error_msg_empty_pw)
                        mBinding.inputLayoutCheckPassword.requestFocus()
                    }

                    !mBinding.checkPw.equals(mBinding.pw) -> {
                        mBinding.inputLayoutCheckPassword.error =
                            getString(R.string.error_msg_not_match_pw)
                        mBinding.inputLayoutCheckPassword.requestFocus()
                    }

                    !mBinding.useAgreementCheckBox.isChecked -> {
                        mBinding.useAgreementCheckBox.error = "동의점요"
                        mBinding.useAgreementCheckBox.requestFocus()
                    }

                    else -> {
                        mAuthViewModel.sendRegisterRequest(
                            RegisterRequest(
                                "${mBinding.email}${getString(R.string.text_school_email_address)}",
                                mBinding.pw!!,
                                mBinding.name!!,
                                true
                            )
                        )
                    }
                }
            }

            override fun onClickShowTerms1() {
                MessageDialog(
                    getString(R.string.text_success_register),
                    getString(R.string.msg_success_register),
                    false,
                    getString(R.string.btn_ok),
                    null,
                    this@RegisterActivity
                ).show(supportFragmentManager)
            }

            override fun onClickShowTerms2() {
                TODO("약관 Dialog 이동")
            }

        })
    }

    override fun observerLiveData() {
        super.observerLiveData()
        mAuthViewModel.registerSuccessEvent.observe(this, Observer {
            logOut()
            MessageDialog(getString(R.string.text_success_register),getString(R.string.msg_success_register),false, getString(R.string.btn_ok), null, this).show(supportFragmentManager)
        })
    }

    override fun onErrorEvent(e: Throwable) {
        super.onErrorEvent(e)
        if(e.message.equals("중복된 이메일")){
            mBinding.inputLayoutEmail.error = getString(R.string.msg_of_no_use_email)
            mBinding.inputLayoutEmail.requestFocus()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUI()
    }

    private fun initUI(){
        mBinding.inputName.addTextChangedListener(object : SimpleTextWatcher() {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                mBinding.inputLayoutPassword.error = null
            }
        })

        mBinding.inputEmail.addTextChangedListener(object : SimpleTextWatcher() {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                mBinding.inputLayoutEmail.error = null
            }
        })

        mBinding.inputPassword.addTextChangedListener(object : SimpleTextWatcher() {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                mBinding.inputLayoutPassword.error = null
            }
        })


        mBinding.inputCheckPassword.addTextChangedListener(object : SimpleTextWatcher() {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                mBinding.inputLayoutPassword.error = null
            }
        })
    }
}
