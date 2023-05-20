# Team Vista's Andie

Contributors: *Liam Hayward, Jacob Myron, Josiah Astwood, Orion Soti, Tyler Birkett*

https://cosc202-team-vista.cspages.otago.ac.nz/andie/docs/

Icons by <a target="_blank" href="https://icons8.com">Icons8</a> and <a target="_blank" href="https://www.flaticon.com">FlatIcon</a>
----
## Getting Started:

>To get started navigate to the file menu and open the image you wish to edit.  
>There are many ways to edit your image from changing colour values, applying filters and transforming your image.
>These can be found in the corresponding menus, descriptions of each action can be found in the features section.  
>You can undo and redo any action you've made using the edit menu and adjust the view of your image in the view menu.
>You could also use the added button shortcuts for the view menu to zoom in, zoom out and reset zoom.
>(Note! view actions do not alter the image, only the way it is displayed within the application).
>Supported File Types Include: PNG, JPG, JPEG, BMP, GIF

## HotKeys:

>Save(Ctrl+S)
>SaveAs(Ctrl+Shift+S)
>Open(Ctr+O)
>Export(Ctrl+Shift+E) 
>Undo(Ctrl+Z)
>Redo(Ctrl+Y) 
>Rotate(Ctrl+R)
>ZoomIn(Ctrl + =)
>ZoomOut(Ctrl + -)
>ZoomFull(Ctrl + 1)
>Crop(Ctr + Shift + C)
---

## Part 1 Features

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

---

<br/>

## Part 2 Features
**Covolution-based Filters Update**
> **Mean Filter**
>> *Description:* Replaces each pixel with the mean value of pixels in a surrounding area. Updated to use a newly implemented convolution method to handle the dark edges of the image when using a high radius.
>>
>> *Contributors:* Jacob Myron, Liam Hayward, Tyler Birkett, Orion Soti
>>
>> *Accessed via:* Filter Menu
>>
>> *Testing:* Tested applying multiple instances of the Mean filter as well as testing it alongside other filters and actions. Tested with various radius values in range with images of various sizes.
>>
>> *Errors:* No known errors.
>
> **Gaussian Blur Filter**
>> *Description:* Blurs high frequency areas of the image. Updated to use a newly implemented convolution method to handle the dark edges of the image when using a high radius.
>>
>> *Contributors:* Orion Soti, Tyler Birkett, Jacob Myron, Liam Hayward
>>
>> *Accessed via:* Filter Menu
>>
>> *Testing:* Tested applying multiple instances of the Gaussian Blur filter as well as testing it alongside other filters and actions. Tested on images of various sizes and with various radius values in range.
>>
>> *Errors:* No known errors.

---

<br/>

**Edge Detection Filters**
> **Emboss Filter**
>> *Description:* Creates the effect of an image being pressed into, with the edges being highlighted. There are eight options for this filter to detect the edges at various angles. This is made possible using the newly implemented convolution operator.
>>
>> *Contributors:* Jacob Myron, Tyler Birkett, Liam Hayward, Orion Soti
>>
>> *Accessed via:* Filter Menu
>>
>> *Testing:* Tested on images of various sizes. Tested alongside other filters and actions.

> **Sobel Filter**
>> *Description:* Creates the effect of an image being pressed into, with the edges being highlighted. There are two options for this filter, one is to detect the edges horizontally, and the other is to detect the edges vertically. This is made possible using the newly implemented convolution operator.
>>
>> *Contributors:* Orion Soti, Tyler Birkett, Jacob Myron, Liam Hayward
>>
>> *Accessed via:* Filter Menu
>>
>> *Testing:* Tested both horizontal and vertical edge detection on images of various sizes. Tested alongside other filters and actions. 
>>
>> *Errors:* No known errors.
>
---
<br/>

**Mouse-based Selection Tools**
> **Crop**
>> *Description:* Allows the user to select a rectangular area of the image to crop. The user can click and drag to select the area, and release the mouse to crop the image. The user can also cancel the crop by pressing the escape key. 
>>
>> *Contributors:* Orion Soti, Liam Hayward
>>
>> *Accessed via:* Transform Menu & Toolbar
>>
>> *Testing:* Tested on images of various sizes. Tested with zoomed images. Crop returns an alert message when you try to start a selection outside image bounds. 
>>
>> *Errors:* No known errors.
>
> **Draw**
>> *Description:* Allows the user to draw on the image. The user can select a colour from the colour picker, the line thickness of the shape outline, and an option for the shape to be filled. You click and drag to draw on the image. The user can also cancel the drawing by pressing the escape key.
>>
>> *Contributors:* Liam Hayward, Orion Soti
>>
>> *Accessed via:* Transform Menu & Toolbar
>>
>> *Testing:* Tested on images of various sizes. Tested with zoomed images. Draw returns an alert message when you try to start a selection outside image bounds.
>>
>> *Errors:* No known errors
>
---

<br/>

**Show Us Something**
> **Matrix Filter**
>> *Description:* Enter the matrix with this filter. Creates an effect similar to the one seen in the Matrix movies. This is made possible using the edge detection filters to grab the edges of the image, and then mapping the edges to a grid of characters.
>>
>> *Contributors:* Orion Soti
>>
>> *Accessed via:* Filter Menu
>>
>> *Testing:* Tested on images of various sizes. Tested alongside other filters and actions. 
>>
>> *Errors:* Not much of an error, but depending on the resolution of the image, the characters may appear smaller than expected. 
>
> **Pixelate Filter**
>> *Description:* Creates a pixelated effect on the image.
>>
>> *Contributors:* Tyler Birkett, Jacob Myron, Liam Hayward, Orion Soti
>>
>> *Accessed via:* Filter Menu
>>
>> *Testing:* Tested on images of various sizes. Tested alongside other filters and actions.
>>
>> *Errors:* No known errors.



---


















