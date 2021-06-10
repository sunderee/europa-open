# Europa Open

Meet "Europa Open", app which helps travelers in the European Region navigate various travel
restrictions. Especially useful nowadays when the tourist destinations are opening up again, more
people get vaccinated and life can return to "normal".

This application pulls data from Reopen Europa public API which I have reversed engineered (I'll be
using Retrofit as my HTTP client), cache/store data into a local SQLite database (Room persistence
library), navigate between fragments with the new Navigation component, and wrap up everything with
idiomatic Kotlin; handle concurrency with coroutines, use MVVM design pattern, handle dependency
injection with Koin and (my personal favorite feature) play around with view/data binding.

## Motivation

About two months ago I visited Spain. It was literally the beginning of the third wave, so I was
lucky the restrictions still weren't that bad. Using Reopen Europa website, it occurred to me that
(back then at least) the performance of their web app was horrendous. I was sitting on the airport
during my 3 hour layoff and wondered how come? So, digging into the problem, I drafted (and finished
on my return flight) the Reopen Europa GraphQL API based on Nest.js. It was just a nice attempt at
decreasing the amount of round trips clients required to access relevant data. Since then, the
official API got a welcoming overhaul.

## License

Project is open sourced under the MIT license.