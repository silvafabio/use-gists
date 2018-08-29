Use Gists 1.0.0
=============

Use your Github gists right inside your Intellij IDE!

Copy and paste your gists to the project code you are working on.

Intellij plugins site: https://plugins.jetbrains.com/plugin/11080-use-gists

This project was inspired on the plugin [getGists][get-gists]


Installation
------------

- Using Intellij IDE built-in plugin system:
  - <kbd>File</kbd> > <kbd>Settings</kbd> > <kbd>Plugins</kbd> > <kbd>Browse repositories...</kbd> > <kbd>Search for "Use Gists"</kbd> > <kbd>Install Plugin</kbd>
- Manually:
  - Download the [latest release][latest-release] and install it manually using <kbd>File</kbd> > <kbd>Settings</kbd> > <kbd>Plugins</kbd> > <kbd>Install plugin from disk...</kbd>
  
Restart IDE.


Usage
-----

   ![Usage](https://s3-sa-east-1.amazonaws.com/cdn.fabioluis.com.br/use-gists/use-gists-how-to-use.gif)


Changelog
---------

### [v1.0.0](https://github.com/silvafabio/use-gists/tree/v1.0.0) (2018-08-28)

- First release


[Full Changelog History](./CHANGELOG.md)


Contribution
------------

Check [`CONTRIBUTING.md`](./CONTRIBUTING.md) file.

### Compiling the source code

To build the plugin run:

    gradle build
    
All required dependencies are downloaded in the background and triggered properly
during the build process. You can also test the plugin easily with running:

    gradle runIdea
    
All of the gradle tasks can be connected to the IntelliJ debugger, so the development process is very easy.


Donate
-------

Support us with a donation.

[![Donate][badge-paypal-img]][badge-paypal]


License
-------

Copyright (c) 2018 Fabio Silva. See the [LICENSE](./LICENSE) file for license rights and limitations.


[get-gists]:              https://github.com/johna1203/getGists    
[latest-release]:         https://github.com/silvafabio/use-gists/releases/latest
[badge-paypal-img]:       https://img.shields.io/badge/donate-paypal-yellow.svg
[badge-paypal]:           https://www.paypal.com/cgi-bin/webscr?cmd=_donations&business=W8NQQ9AFX8NSC&lc=US&item_name=Fabio%20Silva&item_number=Use%20Gists&currency_code=USD&bn=PP%2dDonationsBF%3abtn_donateCC_LG%2egif%3aNonHosted
