package allanksr.com.api_endpoint.ui.screens

import allanksr.com.api_endpoint.R
import allanksr.com.api_endpoint.common.Constants.maxPromoLength
import allanksr.com.api_endpoint.common.Status
import allanksr.com.api_endpoint.common.StringResource.loading
import allanksr.com.api_endpoint.common.StringResource.make_request
import allanksr.com.api_endpoint.common.StringResource.promotion_applied
import allanksr.com.api_endpoint.common.StringResource.promotion_expired
import allanksr.com.api_endpoint.common.StringResource.promotion_is_still_valid
import allanksr.com.api_endpoint.common.StringResource.promotional_code
import allanksr.com.api_endpoint.common.StringResource.remove_item
import allanksr.com.api_endpoint.common.TestTags
import allanksr.com.api_endpoint.common.toastLong
import allanksr.com.api_endpoint.common.toastShort
import allanksr.com.api_endpoint.data.remote.local.PromotionalItem
import allanksr.com.api_endpoint.ui.MainViewModel
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val localLifecycle = LocalLifecycleOwner.current
    val focusManager = LocalFocusManager.current

    var promotionItem by remember {
        mutableStateOf(PromotionalItem())
    }

    val counter = remember {
        mutableStateOf("")
    }

    val textInfo = remember {
        mutableStateOf("")
    }

    val circularControl = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = true) {
        viewModel.endPointApiResponseStatus.observe(localLifecycle) { eventResourcePromotion ->
            val result = eventResourcePromotion.getContentIfNotHandled()
            when (result?.status) {
                Status.LOADING -> {
                    circularControl.value = true
                    context.toastShort(result.message.toString())
                }
                Status.ERROR -> {
                    circularControl.value = false
                    textInfo.value = result.message.toString()
                    context.toastShort(result.message.toString())
                }
                Status.SUCCESS -> {
                    circularControl.value = false
                    val listSize = result.data!!.promo.size
                    viewModel.insertPromotionalItemIntoDb(
                        PromotionalItem(
                            time_was_applied = "${System.currentTimeMillis()}",
                            total_time = "${result.data.promo[listSize - 1].promoData}",
                            promotion_type = result.data.promo[listSize - 1].promoType
                        )
                    )
                    textInfo.value = promotion_applied
                }
                else -> Unit
            }
        }

        viewModel.promotionalItems.observe(localLifecycle) { listItem ->
            if (listItem.isNotEmpty()) {
                listItem.onEach { item ->
                    viewModel.counterTime.observe(localLifecycle) {
                        counter.value = it.getContentIfNotHandled()?.data.toString()
                    }

                    promotionItem = item
                    viewModel.runningTime.value = true
                    viewModel.counter(
                        item.time_was_applied.toLong(),
                        item.total_time.toLong(),
                    )
                }
            }
            if (promotionItem.total_time.isNotEmpty()) {
                if (System.currentTimeMillis() - promotionItem.time_was_applied.toLong() < promotionItem.total_time.toLong()) {
                    //APPLYING PROMOTION
                    context.toastLong(promotion_is_still_valid)
                } else {
                    context.toastLong(promotion_expired)
                    viewModel.deletePromotionalItem(promotionItem)
                    promotionItem = PromotionalItem()
                    viewModel.runningTime.value = false
                    viewModel.counterTime.removeObservers(localLifecycle)
                }
            }
        }

    }

    var promotionText by rememberSaveable { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

    Column(
        modifier = Modifier.fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            },
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Center,
                content = {
                    Text(
                        text = textInfo.value.ifEmpty { stringResource(id = R.string.app_name) },
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontWeight = FontWeight.Normal,
                            fontSize = 30.sp,
                            color = MaterialTheme.colors.onBackground
                        )
                    )
                }
            )
            if (promotionItem.total_time.isEmpty()) {
                Box(modifier = Modifier.padding(10.dp),
                    content = {
                        TextField(
                            modifier = Modifier
                                .focusRequester(focusRequester)
                                .padding(horizontal = 0.dp, vertical = 0.dp),
                            value = promotionText,
                            onValueChange = {
                               // if (it.length < maxPromoLength)
                                    promotionText = it
                            },
                            label = {
                                Text(
                                    color = MaterialTheme.colors.onBackground,
                                    text = promotional_code,
                                    style = TextStyle(color = MaterialTheme.colors.primary),
                                    fontWeight = FontWeight.Thin,
                                    fontSize = 15.sp,
                                )
                            },
                            textStyle = TextStyle(
                                color = MaterialTheme.colors.onBackground,
                                fontWeight = FontWeight.Bold
                            ),
                        )
                    }
                )

                if (!circularControl.value) {
                    Box(modifier = Modifier.padding(10.dp),
                        content = {
                            Button(
                                modifier = Modifier
                                    .testTag(TestTags.MAKE_REQUEST),
                                onClick = {
                                    viewModel.insertPromotionalItem(promotionText)
                                    focusManager.clearFocus()
                                    promotionText = ""
                                },
                                content = {
                                    Text(
                                        modifier = Modifier.padding(
                                            start = 5.dp,
                                            top = 5.dp,
                                            end = 5.dp,
                                            bottom = 5.dp,
                                        ),
                                        text = make_request,
                                        style = TextStyle(
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 20.sp,
                                            color = MaterialTheme.colors.onBackground
                                        )
                                    )
                                }
                            )
                        }
                    )
                }else{
                    Box(modifier = Modifier.padding(10.dp),
                        content = {
                            CircularProgressIndicator(
                                modifier = Modifier.clickable {
                                    context.toastShort(loading)
                                }
                            )
                        }
                    )
                }
            }

            if (promotionItem.total_time.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .padding(10.dp)
                        .testTag(TestTags.LISTEN_RESPONSE),
                    content = {
                        Text(
                            modifier = Modifier
                                .padding(
                                    start = 5.dp,
                                    top = 5.dp,
                                    end = 5.dp,
                                    bottom = 5.dp,
                                ),
                            text = counter.value,
                            style = TextStyle(
                                fontWeight = FontWeight.Normal,
                                fontSize = 25.sp,
                                color = MaterialTheme.colors.onBackground,
                                fontFamily = FontFamily(Font(R.font.odibee_sans_regular))
                            )
                        )
                    }
                )
                Box(
                    modifier = Modifier
                        .padding(10.dp)
                        .testTag(TestTags.LISTEN_RESPONSE),
                    content = {
                        Button(
                            modifier = Modifier.testTag(TestTags.MAKE_REQUEST),
                            onClick = {
                                viewModel.deletePromotionalItem(promotionItem)
                                promotionItem = PromotionalItem()
                                viewModel.runningTime.value = false
                                viewModel.counterTime.removeObservers(localLifecycle)
                            },
                            content = {
                                Text(
                                    modifier = Modifier.padding(
                                        start = 5.dp,
                                        top = 5.dp,
                                        end = 5.dp,
                                        bottom = 5.dp,
                                    ),
                                    text = remove_item,
                                    style = TextStyle(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 20.sp,
                                        color = MaterialTheme.colors.onBackground
                                    )
                                )
                            }
                        )
                    }
                )
            }
        }
    )
}