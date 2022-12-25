package com.jalloft.cryptoprices.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.jalloft.cryptoprices.R
import com.jalloft.cryptoprices.model.Currency
import com.jalloft.cryptoprices.remote.DataResource
import com.jalloft.cryptoprices.ui.theme.*
import com.jalloft.cryptoprices.ui.viewmodel.CryptoCurrencyViewModel
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    viewModel: CryptoCurrencyViewModel = viewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            )
        },
    ) { innerPadding ->
        val dataResource by viewModel.dataResource.observeAsState(DataResource.loading())
        when (dataResource.status) {
            DataResource.Status.LOADING -> {
                LoadingScreen(innerPadding)
            }
            DataResource.Status.SUCCESS -> {
                ListScreen(dataResource.data, innerPadding)
            }
            DataResource.Status.ERROR -> {
                ErrorScreen(innerPadding)
            }
        }
    }
}

@Composable
fun ErrorScreen(innerPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = R.drawable.illustration_error,
            contentDescription = stringResource(id = R.string.description_error),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.default_error_message),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center
            )
        }

    }
}

@Composable
fun LoadingScreen(innerPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
        Text(
            text = stringResource(id = R.string.loading),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun ListScreen(list: List<Currency>?, innerPadding: PaddingValues) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = innerPadding
    ) {
        list?.let {
            items(it) { currency ->
                CurrencyRowItem(currency)
            }
        }
    }
}

@Composable
fun CurrencyRowItem(currency: Currency) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSystemInDarkTheme()) Jet else White,
        ),
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                AsyncImage(
                    model = currency.iconUrl,
                    contentDescription = currency.name,
                    modifier = Modifier.size(28.dp),
                )
                Text(
                    text = DecimalFormat("#.#").format(currency.percentChangeInOneHour.percentage)
                        .plus("%"),
                    modifier = Modifier
                        .background(
                            if (currency.percentChangeInOneHour.percentageUp) TeaGreen else LightRed,
                            RoundedCornerShape(20.dp)
                        )
                        .padding(8.dp, 0.dp),
                    color = if (currency.percentChangeInOneHour.percentageUp) Mantis else FireOpal,
                    fontWeight = FontWeight.Bold,
                )
            }
            Text(
                text = currency.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Row {
                val currencySymbol =
                    NumberFormat.getCurrencyInstance(Locale.US).currency?.symbol?.toString()!!
                Text(
                    text = currencySymbol,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Text(
                    text = NumberFormat.getCurrencyInstance(Locale.US)
                        .format(currency.price).replace(currencySymbol, ""),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
            Text(
                text = stringResource(id = R.string.volume_in_24_hours),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )
            Row {
                val currencySymbol =
                    NumberFormat.getCurrencyInstance(Locale.US).currency?.symbol?.toString()!!
                Text(
                    text = currencySymbol,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(end = 4.dp)
                )
                Text(
                    text = NumberFormat.getCurrencyInstance(Locale.US)
                        .format(currency.dayValume).replace(currencySymbol, ""),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.ExtraBold
                )

            }

        }
    }
}