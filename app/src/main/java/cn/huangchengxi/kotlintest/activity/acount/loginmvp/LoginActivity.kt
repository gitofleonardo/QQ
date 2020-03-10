package cn.huangchengxi.kotlintest.activity.acount.loginmvp

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.huangchengxi.kotlintest.R
import cn.huangchengxi.kotlintest.activity.acount.registermvp.RegActivity
import cn.huangchengxi.kotlintest.activity.main.MainActivity
import cn.huangchengxi.kotlintest.adapter.LoginAccountAdapter
import cn.huangchengxi.kotlintest.beans.LoginAccountBean
import cn.huangchengxi.kotlintest.databean.LocalUserDBBean
import cn.huangchengxi.kotlintest.localutils.SqliteHelper
import cn.huangchengxi.kotlintest.localutils.TextValidator
import kotlinx.android.synthetic.main.view_login_account.*

class LoginActivity : AppCompatActivity(),LoginCallback{
    //widget
    private val uid by lazy { findViewById<EditText>(R.id.uid) }
    private val password by lazy{findViewById<EditText>(R.id.password)}
    private val loginButton by lazy { findViewById<CardView>(R.id.login_button) }
    private val forget by lazy { findViewById<TextView>(R.id.forget_password) }
    private val register by lazy { findViewById<TextView>(R.id.register) }
    private val loginBg by lazy { findViewById<View>(R.id.login_button_bg) }
    private val showAccountArrow by lazy { findViewById<FrameLayout>(R.id.show_accounts) }
    private val showAccountArrowImg by lazy{findViewById<ImageView>(R.id.show_accounts_img)}
    private val accountRv by lazy { findViewById<RecyclerView>(R.id.account_rv)}
    private val accountLayoutManager by lazy { LinearLayoutManager(this) }
    private val bottomPasswordAndReg by lazy { findViewById<FrameLayout>(R.id.bottom_password_and_reg) }
    private val loginParentView by lazy { findViewById<LinearLayout>(R.id.login_parent) }
    private val currentPotraitImg by lazy { findViewById<ImageView>(R.id.current_portrait) }
    private val portraitImgParent by lazy { findViewById<CardView>(R.id.portrait_parent) }
    private val clearAccountBtn by lazy { findViewById<ImageView>(R.id.clear_account) }
    private val passwordEye by lazy { findViewById<ImageView>(R.id.password_eye) }

    //value
    private val presenter:LoginPresenter= LoginPresenter(this)
    private val accountList=ArrayList<LoginAccountBean>()
    private val loginAccountAdapter=LoginAccountAdapter(accountList)
    private var isBottomShown=true
    private var isAccountPassValidate=false
    private var passwordVisible=false
    private var firstEditPassword=true

    //object
    private val watcher=object : TextWatcher{
        override fun afterTextChanged(p0: Editable?) {
            val name=this@LoginActivity.uid.text.toString()
            val passwd=this@LoginActivity.password.text.toString()
            if (TextValidator.checkUid(name) && TextValidator.checkPassword(passwd)){
                changeLoginButtonBg(true)
            }else{
                changeLoginButtonBg(false)
            }
            if (name.isNotEmpty()){
                if (uid.isFocused)
                    clearAccountBtn.visibility=View.VISIBLE
                else
                    clearAccountBtn.visibility=View.GONE

                val user=SqliteHelper.queryUser(this@LoginActivity,name)
                Log.e("validate user","...")
                if (user!=null){
                    uid.removeTextChangedListener(this)
                    isAccountPassValidate=true
                    portraitImgParent.visibility=View.VISIBLE
                    uid.text=SpannableStringBuilder(user.name)
                    password.text=SpannableStringBuilder(user.password)
                    uid.addTextChangedListener(this)

                    firstEditPassword=true
                }else{
                    portraitImgParent.visibility=View.GONE
                    isAccountPassValidate=false

                    uid.removeTextChangedListener(this)
                    password.text=SpannableStringBuilder("")
                    uid.addTextChangedListener(this)
                }
            }else{
                isAccountPassValidate=false
                clearAccountBtn.visibility=View.GONE
                portraitImgParent.visibility=View.GONE
            }
        }
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    }
    private val passwordWatcher=object : TextWatcher{
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }
        override fun afterTextChanged(s: Editable?) {
            if (TextValidator.checkPassword(password.text.toString()) && TextValidator.checkUid(uid.text.toString())){
                changeLoginButtonBg(true)
                Log.e("enable",true.toString())
            }else{
                Log.e("disable",false.toString())
                changeLoginButtonBg(false)
            }
            if (password.text.toString().isEmpty())
                firstEditPassword=false
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_login)
        init()
    }
    private fun init(){
        uid.addTextChangedListener(watcher)
        password.addTextChangedListener(passwordWatcher)
        loginButton.setOnClickListener {
            presenter.login()
        }
        register.setOnClickListener {
            val intent=Intent(this,
                RegActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_START_REGISTER)
        }
        showAccountArrow.setOnClickListener {
            if (isBottomShown)
                showAccountRv()
            else
                hideAccountRv()
        }
        loginAccountAdapter.setOnClickListener {
            setPasswordInvisible()

            val item=accountList[it]
            uid.text=SpannableStringBuilder(item.account)
            password.text=SpannableStringBuilder(item.password)
            firstEditPassword=true
            hideAccountRv()
        }
        loginAccountAdapter.setOnDeleteListener {
            val item=accountList[it]
            SqliteHelper.deleteUser(this,item.account)
            accountList.removeAt(it)
            loginAccountAdapter.notifyItemRemoved(it)
        }
        loginParentView.setOnClickListener {
            hideAccountRv()
        }
        clearAccountBtn.setOnClickListener {
            uid.text=SpannableStringBuilder("")
        }
        passwordEye.setOnClickListener {
            if (passwordVisible){
                setPasswordInvisible()
            }else{
                if (firstEditPassword){
                    setPasswordVisibleAndClear()
                    firstEditPassword=false
                }else{
                    setPasswordVisible()
                }
            }
        }
        accountRv.adapter=loginAccountAdapter
        accountRv.layoutManager=accountLayoutManager
        val fromUid=intent.getStringExtra("uid")
        val fromPassword=intent.getStringExtra("password")
        uid.setText(fromUid?:"")
        password.setText(fromPassword?:"")

        uid.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus){
                if (uid.text.toString().isNotEmpty()){
                    clearAccountBtn.visibility=View.VISIBLE
                }
            }else{
                clearAccountBtn.visibility=View.GONE
            }
        }
        password.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus){
                passwordEye.visibility=View.VISIBLE
            }else{
                passwordEye.visibility=View.GONE
            }
        }
    }
    private fun setPasswordInvisible(){
        passwordVisible=false
        password.inputType=InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
        passwordEye.setImageResource(R.drawable.eye_closed)
    }
    private fun setPasswordVisible(){
        password.inputType=InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        passwordVisible=true
        passwordEye.setImageResource(R.drawable.eye_open)
    }
    private fun setPasswordVisibleAndClear(){
        password.text=SpannableStringBuilder("")
        password.inputType=InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        passwordVisible=true
        passwordEye.setImageResource(R.drawable.eye_open)
    }
    private fun showAccountRv(){
        portraitImgParent.visibility=View.GONE
        accountList.clear()
        accountList.addAll(SqliteHelper.queryUsers(this))
        if (accountList.size>0){
            loginAccountAdapter.notifyItemRangeInserted(0,accountList.size)
            loginAccountAdapter.notifyItemRangeChanged(0,accountList.size)

            //loginAccountAdapter.notifyDataSetChanged()
        }
        isBottomShown=false
        bottomPasswordAndReg.visibility=View.GONE
        reverseArrow()
    }
    private fun hideAccountRv(){
        if (!isBottomShown){
            if (isAccountPassValidate)
                portraitImgParent.visibility=View.VISIBLE
            accountList.clear()
            loginAccountAdapter.notifyDataSetChanged()
            bottomPasswordAndReg.visibility=View.VISIBLE
            isBottomShown=true
            reverseArrow()
        }
    }
    private fun reverseArrow(){
        if (!isBottomShown){
            showAccountArrowImg.rotation=180F
        }else{
            showAccountArrowImg.rotation=0F
        }
    }
    private fun changeLoginButtonBg(enabled:Boolean){
        Log.e("set enable",enabled.toString())
        loginButton.isEnabled=enabled
        loginButton.isClickable=enabled
        if (enabled){
            loginBg.background=getDrawable(R.drawable.login_enabled_button_bg)
        }else{
            loginBg.background=getDrawable(R.drawable.login_disabled_button_bg)
        }
    }

    override fun getUser(): LocalUserDBBean ?{
        val uid=this.uid.text.toString()
        val password=this.uid.text.toString()
        return if (TextValidator.checkPassword(password) && TextValidator.checkUid(uid)) LocalUserDBBean(uid,password) else null
    }

    override fun onLoginSuccess() {
        SqliteHelper.updateUser(this,uid.text.toString(),password.text.toString())
        val intent=Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onLoginFailed() {
        Toast.makeText(this,"登录失败",Toast.LENGTH_SHORT).show()
    }

    override fun onFormatError() {
        Toast.makeText(this,"账号或密码格式错误",Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            REQUEST_CODE_START_REGISTER->{
                if (data!=null){
                    uid.text=SpannableStringBuilder(data.getStringExtra("account")?:uid.text.toString())
                    password.text=SpannableStringBuilder(data.getStringExtra("password")?:password.text.toString())
                }
            }
        }
    }
    companion object{
        private const val REQUEST_CODE_START_REGISTER=0

        fun startLoginActivity(context: Context,name:String,password:String){
            val intent=Intent(context,
                LoginActivity::class.java)
            intent.putExtra("uid",name)
            intent.putExtra("password",password)
            context.startActivity(intent)
        }
        fun startLoginActivity(context: Context){
            val intent=Intent(context,
                LoginActivity::class.java)
            context.startActivity(intent)
        }
    }
}
