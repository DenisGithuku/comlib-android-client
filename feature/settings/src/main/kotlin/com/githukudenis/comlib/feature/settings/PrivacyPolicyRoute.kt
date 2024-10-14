
/*
* Copyright 2023 Denis Githuku
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* https://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.githukudenis.comlib.feature.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.githukudenis.comlib.core.designsystem.ui.components.loading_indicators.CLibCircularProgressBar
import com.githukudenis.comlib.core.designsystem.ui.theme.LocalDimens
import com.kevinnzou.web.LoadingState
import com.kevinnzou.web.WebView
import com.kevinnzou.web.rememberWebViewState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyPolicyRoute(onNavigateUp: () -> Unit) {
    val state = rememberWebViewState(url = "https://sites.google.com/view/gitsoftapps-comlib/home")

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.privacy_title),
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onNavigateUp() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        if (state.loadingState is LoadingState.Loading) {
            Box(
                modifier =
                    Modifier.fillMaxSize()
                        .padding(innerPadding)
                        .padding(horizontal = LocalDimens.current.sixteen),
                contentAlignment = Alignment.Center
            ) {
                CLibCircularProgressBar()
            }
        }

        Column(
            modifier =
                Modifier.fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = LocalDimens.current.sixteen)
        ) {
            WebView(state = state)
        }
    }
}
