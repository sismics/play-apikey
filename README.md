# play-apikey plugin

[![GitHub release](https://img.shields.io/github/release/sismics/play-apikey.svg?style=flat-square)](https://github.com/sismics/play-apikey/releases/latest)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

# play-apikey plugin

This module allows authentication via an API Key in Play! Framework 1 REST applications.

# How to use

####  Add the dependency to your `dependencies.yml` file

```
require:
    - apikey -> apikey 1.1.0

repositories:
    - apikey:
        type:       http
        artifact:   "http://release.sismics.com/repo/play/[module]-[revision].zip"
        contains:
            - apikey -> *
```

####  Specify the API Key in your HTTP requests

Add the following header to your HTTP requests:

```
Authentication: Token THE_API_KEY
```

####  Customize the authentication mechanism

Extend the class ApiKeySecure.Security and implement the `authenticate` method.

####  Secure your controllers

Add the following annotations to secure your controllers:

```
@With(ApiKeySecure.class)
public class MyThings extends Controller {
    @CheckApiKey
    public static void listThings() {
        // ...
    }

}
```

# License

This software is released under the terms of the Apache License, Version 2.0. See `LICENSE` for more
information or see <https://opensource.org/licenses/Apache-2.0>.
