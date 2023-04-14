13-April-2023

Hello! I am not here for this demo, so sorry.

Device is an Arduino Uno, please take note of the COM Port it is on.

Before compiling and uploading the sketch, the following libraries need to be installed to the IDE:
    Protothreads by Ben Artin and Adam Dunkels (V 1.4.0-arduino.beta.1)
    DHT20 by Rob Tillaart (V 0.2.2)
Everything should compile and upload with these.

One line of code needs to be changed in python script 'Serial_test.py'
    Line 86 - setupSerial(9600, "COMX")
COM port needs to be set according to what the Arduino is using. 
I have only run this on Windows OS, I know COM port formatting is different for Apple OS

Need to install PySerial for script to run
    python -m pip install pyserial
Run script using python3
All entry values should range from 0.0-100.0, but the program checks for that.
I didn't make any changes to the python script.

You can run this in the Serial Monitor in the Arduino IDE, but all entries need to be in the format '<xx.xx>'

Currently the DHT20 thread runs every 10 seconds, solely for testing purposes. 
This should be changed to run every 5 minutes (300000ms) from LiangLiang's suggestion.
Temperature and Humidity data is printed to serial in the format '<Humidity, Temperature>'
If you want anything printed to serial it needs to be in the format above (uses </> as start/end characters).

Hardware Notes:
    DO NOT!!!!!! plug the DC barrel into the Arduino, it needs to go on the jack on the green shield (PCB).
    I could not unsolder the jack on the Arduino without causing serious damage so I stopped trying.
    The fan gets plugged into the bent header pins on the left of the DHT20. The '1' on the fan header should be facing up.
    Black wire gets plugged in on the left, yellow wire gets plugged in on the right. It should click into place.
    The fit is wierd with the DHT20 but I'm trying to see what I can do about changing out the fan header from 4 pin to 2 pin. 
    
Additional Software Notes:
    There's some sort of lock going on in the python script. 
    Script waits to read values from serial until after it's written to. I have example of this in my notebook. 
    Didn't have time to look at that this week but will do some investigating this weekend. 
    Everything is running correctly as seen in Serial Monitor.

That should be it. Cool beans :p
You might be able to reach me if there's a problem you call/text sometime before 11:40 but after then I'll be driving.
I tried to make this file as comprehensive as possible.

-- Milly Buchanan