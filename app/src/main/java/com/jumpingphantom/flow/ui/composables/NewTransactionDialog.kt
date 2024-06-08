package com.jumpingphantom.flow.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.text.isDigitsOnly
import com.jumpingphantom.flow.R
import com.jumpingphantom.flow.data.entity.Transaction
import com.jumpingphantom.flow.viewmodel.TransactionViewmodel
import kotlinx.coroutines.DelicateCoroutinesApi
import java.time.LocalDate

@Composable
fun NewTransactionDialog(
    showDialog: MutableState<Boolean>,
    viewmodel: TransactionViewmodel
) {

    val amount = remember { mutableStateOf("") }
    val isIncomeSelected = remember { mutableStateOf(true) }
    val isExpenseSelected = remember { mutableStateOf(false) }

    if (showDialog.value) {
        Dialog(onDismissRequest = { showDialog.value = false }) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.wrapContentSize()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth()
                    ) {
                        TransactionTypeButtons(
                            isIncomeSelected = isIncomeSelected,
                            isExpenseSelected = isExpenseSelected
                        )
                    }

                    Spacer(modifier = Modifier.padding(8.dp))

                    Row(modifier = Modifier) {
                        if (isExpenseSelected.value) {
                            ExpensesCategoryMenu()
                        } else {
                            IncomeCategoryMenu()
                        }

                        AmountInputField(amount)
                    }

                    Spacer(modifier = Modifier.padding(8.dp))

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        AddTransactionButton(showDialog, amount, isIncomeSelected, viewmodel)
                    }
                }
            }
        }
    }
}

@Composable
fun TransactionTypeButtons(
    isIncomeSelected: MutableState<Boolean>,
    isExpenseSelected: MutableState<Boolean>
) {
    ElevatedFilterChip(
        selected = isIncomeSelected.value,
        onClick = {
            if (isExpenseSelected.value) {
                isExpenseSelected.value = false
            }
            isIncomeSelected.value = true
        },
        label = {
            Text(
                text = stringResource(R.string.income),
                fontSize = MaterialTheme.typography.labelLarge.fontSize,
                modifier = Modifier.padding(16.dp)
            )
        }
    )

    ElevatedFilterChip(
        selected = isExpenseSelected.value,
        onClick = {
            if (isIncomeSelected.value) {
                isIncomeSelected.value = false
            }
            isExpenseSelected.value = true
        },
        label = {
            Text(
                text = stringResource(R.string.expense),
                fontSize = MaterialTheme.typography.labelLarge.fontSize,
                modifier = Modifier.padding(16.dp)
            )
        }
    )
}

@Composable
fun AmountInputField(amount: MutableState<String>) {
    TextField(
        value = amount.value,
        onValueChange = { newValue ->
            if (newValue.isDigitsOnly() or newValue.contains('.')) {
                if (newValue.count { it == '.' } <= 1) {
                    amount.value = newValue
                }
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        prefix = {
            Text(
                text = "$",
                fontWeight = MaterialTheme.typography.headlineLarge.fontWeight
            )
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun ExpensesCategoryMenu() {
    // TODO : implement this
}

@Composable
fun IncomeCategoryMenu() {
    // TODO : implement this
}

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun AddTransactionButton(
    showDialog: MutableState<Boolean>,
    amount: MutableState<String>,
    isIncomeSelected: MutableState<Boolean>,
    viewmodel: TransactionViewmodel
) {
    Button(
        onClick = {
            if (amount.value != "" && amount.value.toFloat() > 0.009) {
                addTransaction(
                    amount.value.toFloat(),
                    "",
                    "",
                    LocalDate.now(),
                    isIncomeSelected.value,
                    viewmodel
                )
            }
            showDialog.value = false
        },
        shape = MaterialTheme.shapes.small,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("New transaction")
        Icon(imageVector = Icons.Filled.Add, contentDescription = "Add new transaction button")
    }
}

fun addTransaction(
    amount: Float,
    category: String?,
    description: String?,
    date: LocalDate,
    isIncome: Boolean,
    viewModel: TransactionViewmodel
) {
    val transaction = Transaction(0, amount, category, description, date, isIncome)
    viewModel.setTransaction(transaction)
    viewModel.updateIncomeAndExpenses()
}