# Team Vista's Andie

Contributors: *Liam Hayward, Jacob Myron, Josiah Astwood, Orion Soti, Tyler Birkett*

https://cosc202-team-vista.cspages.otago.ac.nz/andie/docs/

----
## Getting Started:

>To get started navigate to the file menu and open the image you wish to edit.  
>There are many ways to edit your image from changing colour values, applying filters and transforming your image.
>These can be found in the corresponding menus, descriptions of each action can be found in the features section.  
>You can undo and redo any action you've made using the edit menu and adjust the view of your image in the view menu.
>You could also use the added button shortcuts for the view menu to zoom in, zoom out and reset zoom.
>(Note! view actions do not alter the image, only the way it is displayed within the application).
>Supported File Types Include: PNG, JPG, JPEG, BMP, GIF
>Hotkeys: Save(Ctrl+S), SaveAs(Ctrl+Shift+S), Open(Ctr+O), Export(Ctrl+Shift+E),           Undo(Ctrl+Z), Redo(Ctrl+Y)
---

## Features

**Sharpen Filter**

> *Description:* Adjusts the sharpness of the image
>
> *Contributors:* Orion Soti
>
> *Acccessed via:* Filter Menu
>
> *Testing:* Tested with other filter and colour changes
>
> *Errors:* No known errors
---


**Gaussian Blur Filter**

> *Description:* Blurs high frequency areas of the image
>
> *Contributors:* Orion Soti
>
> *Accessed via:* Filter Menu
>
> *Testing:* Tested it along with other image operations
>
> *Errors:* No known errors
---

**Median Filter**

> *Description:* Replaces each pixel with the median value of pixels in a surrounding area.
>
> *Contributors:* Jacob Myron
>
> *Accessed via:* Filter Menu
>
> *Testing:* Tested applying multiple instances of the Median filter as well as testing it alongside other filters and actions.
>
> *Errors:* No known errors.
---

**Brightness & Contrast Adjustment**

> *Description:* Adjusts the contrast or brightness of the current image.
>
> *Contributors:* Liam Hayward & Josiah Astwood
>
> *Accessed via:* Colour Menu
>
> *Testing:* Testing involved making various adjustments to contrast and brightness, checking that operations are saved, and adding/testing sliders to limit user input.
>
> *Errors:* No known errors
---

**Multilingual Support**

> *Description:* Allows the changing of language, supports English, Spanish, Maori and Pirate. IT will update in real time.
>
> *Contributors:* Tyler Birkett
> 
> *Accessed via:* File Menu/Language Settings
>
> *Testing:* Testing was completed using JUnit Test for the majority and Refer to the PDF of Testing for GUI Testing
>
> *Errors:* No known errors
---

**Resize**

> *Description:* Click on Resize to open a pop up menu which asks for inputs for scale, width, and height. You can use the spinner or enter values manually by keyboard
>
> *Contributors:* Orion Soti
> 
> *Accessed via:* Transform Menu
>
> *Testing:* Tested it with large values for scale, width, and height values. Also tested it along with other image operations
>
> *Errors:* No known errors
---

**Rotate**

> *Description:* Rotates the image in 90 degree increments to the left or right
>
> *Contributors:* Jacob Myron
> 
> *Accessed via:* Transform menu
>
> *Testing:* Tested rotating the image both before and after applying a mixture of filters, colours and transform effects. Orientation remains the same after export and saving.
>
> *Errors:* No known errors
---

**Flip**

> *Description:* Under the Transform menu, there are two options for flipping the image. One is to flip the image horizontally, and the other is to flip the image vertically
>
> *Contributors:* Orion Soti
> 
> *Accessed via:* Transform Menu
>
> *Testing:* Tested it along with other image operations
>
> *Errors:* No known errors
---

**Image Export**


> *Description:* Alows users to export a copy of the current edited image. 
>
> *Contributors:* Liam Hayward
> 
> *Accessed via:* File Menu or HotKey: CTRL + SHIFT + E.
>
> *Testing:* Tested image types: png, jpg, jpeg, gif all are working. Also set up a check to stop users saving incorrect file types. 
>
> *Errors:* No known errors.
---

**Error Handling**

> *Contributors* Jacob Myron, Liam Hayward, Orion Soti, Josiah Astoowd, Tyler Birkett
>
>*Testing* Known errors purposefully triggered after implementing a fix to ensure the desired outcome.
>
>*Errors* No known errors.
---


**Testing**

> *Description:* Testing was done with a combination of some JUnit Testing and Alot of Manual Testing. Please Refer to the Testing documentation PDF.
>
> *Contributors:* Tyler Birkett
> 
> *Accessed via:* AndieProject/andie/AndieTestingDocumentation.pdf
>
> *Code Refactoring/additions: Any changes to the original methods/classes/skeleton of the program are documented in the Testing doumentation PDF.


