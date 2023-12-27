В данном проекте присутствует 3 апишки: 
/convert - POST-запрос, принимает JSON в request body. Вот пример:
Request:
{
    "amount": 258,
    "fromCurrency": "USD",
    "toCurrencies": ["KZT", "EUR"]
}
Response:
{
    "fromCurrency": "USD",
    "originalAmount": 258,
    "convertedAmounts": {
        "EUR": 234.29,
        "KZT": 117704.76
    },
    "message": ""
}

/rates - GET-запрос, пример Response:
[
    {
        "currency": "KZT",
        "rate": 1.0
    },
    {
        "currency": "USD",
        "rate": 456.22
    },
    {
        "currency": "EUR",
        "rate": 502.39
    }
]

/exchange-history - GET-запрос, пример Response:
[
    {
        "ID": 1,
        "FROM_CURRENCY": "USD",
        "TO_CURRENCY": "KZT",
        "AMOUNT": 258.0,
        "RESULT": 117704.76,
        "OPERATION_TIME": "2023-12-27T15:38:50.299+00:00"
    },
    {
        "ID": 2,
        "FROM_CURRENCY": "USD",
        "TO_CURRENCY": "EUR",
        "AMOUNT": 258.0,
        "RESULT": 234.29,
        "OPERATION_TIME": "2023-12-27T15:38:50.313+00:00"
    }
]
