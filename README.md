A Java Swing library for creating wizards (e.g. installers).

The wizard is made up of two stages:
- Configuration: Put in configuration data on each wizard page.
- Execution: Click *Apply* on the final page. A dialog appears where operations can be started (e.g. a software installation).

# How to use
- Run a demo with DemoWizard.main().
- If you want to use this library to create your own wizard, see the package *demo* for inspiration.

# Classes overview

## Core classes
Each wizard page is represented by a pair of classes:
- Page: Manages the GUI.  
- PageData: Manages the data.

PageData functionality
- Load/Save data from/to storage.
- Load default values.
- Validate all data.

Page functionality
- Build the GUI and fill it with data from PageData. 
- Write changes performed in the GUI back to PageData. 

When validation results are available, the page displays a status bar. 
Clicking the status bar opens a dialog with all validation results.

## Supporting classes
- PagePool: Contains all available pages and manages navigation between them.
- PageDataPool: Contains references to all PageData objects.
- ButtonBar: Contains all buttons at the bottom of the wizard. (Cancel, Back, Next, Apply)
- PageTitleList: A list of all pages.
- PageFrame: The main window of the wizard. It contains 
  - the PageTitleList (left), 
  - the page area (right),
  - the ButtonBar (bottom).

# Image
![Demo Wizard](/images/demo-wizard.png)
