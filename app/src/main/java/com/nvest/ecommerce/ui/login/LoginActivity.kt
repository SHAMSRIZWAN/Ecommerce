package com.nvest.ecommerce.ui.login


import android.content.res.Configuration
import androidx.activity.viewModels
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.nvest.ecommerce.R
import com.nvest.ecommerce.data.network.DataError
import com.nvest.ecommerce.data.network.Success
import com.nvest.ecommerce.ui.base.BaseComponentActivity
import com.nvest.ecommerce.ui.dashboard.DashboardActivity
import com.nvest.ecommerce.ui.registration.RegisterActivity
import com.nvest.ecommerce.ui.theme.EcommerceTheme
import com.nvest.ecommerce.ui.theme.Shapes
import com.nvest.ecommerce.util.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseComponentActivity<LoginViewModel>() {

    override val viewModel: LoginViewModel by viewModels()


    @Composable
    override fun ProvideCompose() {

        observe(viewModel.loginResponse) {
            when (it) {
                is DataError -> {
                }
                is Success -> {
                    startDashboardActivity()
                    finish()
                }


            }
        }

        LoginCompose {
            TopImageAndText()
            var txtAccountNo by remember {
                mutableStateOf("Nvest@Gmail.com")
            }

            TextFieldUserName(txtAccountNo) { txtAccountNo = it }
            var txtPass by remember { mutableStateOf("Nvest@Gmail.com") }

            TextFieldPassword(txtPass) {
                txtPass = it
            }

            RegistrationButton {
                viewModel.onSignInBtnClick(
                    txtAccountNo.trim(),
                    txtPass.trim()
                )
            }

            Spacer(
                modifier = Modifier.height(
                    dimensionResource(R.dimen.dp_5)
                )
            )

            RegistrationText()


        }

    }

     @Composable
     private fun RegistrationText() {

        Text(
            modifier = Modifier
                .width(IntrinsicSize.Max)
                .clickable { startActivity<RegisterActivity> {} },
            textAlign = TextAlign.Center,
            text = stringResource(R.string.or_register),
            style = MaterialTheme.typography.button,
            color = MaterialTheme.colors.primary
        )

    }

    @Composable
    private fun RegistrationButton(onClick: () -> Unit = {}) {

        Button(modifier = Modifier
            .testTag(stringResource(id = R.string.sign_in))
            .width(dimensionResource(R.dimen.dp_150)),
            colors = ButtonDefaults
                .buttonColors(MaterialTheme.colors.primary),
            onClick = { onClick() }) {

            Text(
                text = stringResource(id = R.string.sign_in)
                    .uppercase(),
                color = MaterialTheme.colors.onSecondary
            )
        }

        Spacer(
            modifier = Modifier
                .height(dimensionResource(R.dimen.dp_30))
        )


    }


    @Composable
    private fun TextFieldPassword(txtPass: String, setPass: (String) -> Unit = {}) {


        TextField(modifier = Modifier
            .testTag(stringResource(R.string.password))
            .fillMaxWidth()
            .wrapContentHeight()
            .border(
                shape = Shapes.medium,
                border = BorderStroke(
                    dimensionResource(R.dimen.dp_1),
                    MaterialTheme.colors.primary
                )
            ),
            maxLines = 1,
            singleLine = true,
            value = txtPass,
            visualTransformation = PasswordVisualTransformation(),
            onValueChange = { setPass(it) },
            placeholder = {
                Text(
                    text = stringResource(R.string.password),
                    color = MaterialTheme.colors.primary
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                textColor = MaterialTheme.colors.primary,
                disabledTextColor = Color.Transparent,
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = MaterialTheme.colors.primary
            ),
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_baseline_lock_24),
                    contentDescription = "", tint = MaterialTheme.colors.primary
                )
            })

        Spacer(
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.dp_40))
        )


    }

    @Composable
    private fun TopImageAndText() {
        Image(
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.dp_100)),
            painter = painterResource(id = R.drawable.ic_nvest),
            contentDescription = ""
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp_10)))

        Text(
            modifier = Modifier.width(IntrinsicSize.Max),
            textAlign = TextAlign.Center,
            text = stringResource(R.string.ecommerce),
            style = MaterialTheme.typography.button,
            color = MaterialTheme.colors.primary
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp_50)))


    }

    @Composable
    private fun TextFieldUserName(txtAccountNo: String, setAcc: (String) -> Unit = {}) {


        TextField(modifier = Modifier
            .testTag(stringResource(R.string.email_address))
            .fillMaxWidth()
            .wrapContentHeight()
            .border(
                shape = Shapes.medium,
                border = BorderStroke(
                    dimensionResource(R.dimen.dp_1),
                    MaterialTheme.colors.primary
                )
            ),
            maxLines = 1,
            singleLine = true,
            value = txtAccountNo,
            onValueChange = { setAcc(it) },
            placeholder = {
                Text(
                    text = stringResource(R.string.email_address),
                    color = MaterialTheme.colors.primary
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                textColor = MaterialTheme.colors.primary,
                disabledTextColor = Color.Transparent,
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = MaterialTheme.colors.primary

            ),
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_baseline_person_24),
                    contentDescription = "", tint = MaterialTheme.colors.primary
                )
            })
        Spacer(
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.dp_20))
        )


    }

    private fun startDashboardActivity() {
        startActivity<DashboardActivity> { }
    }

    @Composable
    fun LoginCompose(childrenCompose: @Composable () -> Unit) {


        Column(
            Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.dp_20)),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column (Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,

                ){

                childrenCompose()

            }
        }


    }

    @Preview(
        showBackground = true,
        uiMode = Configuration.UI_MODE_NIGHT_NO
    )
    @Composable
    override fun ProvideComposeLightPreview() {
        EcommerceTheme {
            LoginCompose {

                TopImageAndText()
                TextFieldUserName("")
                TextFieldPassword("")
                RegistrationButton()
                RegistrationText()
            }

        }
    }


}