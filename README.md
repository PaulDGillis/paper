# Paper app for Fetch
### by Paul Gillis

Distributed under Apache 2 license, includes build-logic from now-in-android for build rules to jump start project.

## Module structure breakdown
Based on now in android, but simplified to what I needed at the time to do faster testing/prototyping. 
There are other limited snippets of code from NowInAndroid, didn't want to reinvent wheel for commonly used boilerplate code.
| Module            | Purpose                                                                                                    |
| :---------------- | :--------------------------------------------------------------------------------------------------------: |
| :app              | Binds Activity logic, and starts main feature :feature:feed.                                               |
| :build-logic      | Build project gradle shortcuts to make the modularity in this project easier.                              |
| :core:common      | Now-in-android's common tools that were useful.                                                            |
| :core:data        | Combines :core:network and :core:database repos for one central place for read/write, and data validation. |
| :core:database    | Stores network data on disk for faster lookup.                                                             |
| :core:model       | Contains common data models used throughout the app.                                                       |
| :core:network     | Fetches the paper item feed from Fetch's servers and parses it. No data model validation, see :core:data   |
| :feature:feed     | Contains compose Ui Screen and Viewmodel for displaying paper's item feed.                                 |

The modules are setup this way for easier prototyping, while keeping each module's tests grouped together by what they're testing.
There are some basic tests I setup for testing modules as I made them. See :core:data, :core:database, :core:network.