## SB Motor Shield for Android things

https://www.youtube.com/watch?v=ElelbdAgl7E

This repo ports https://github.com/sbcshop/motor-shield/ for Android Things for Raspberry PI.

Code is written for controlling two motors car with keyboard. You can easily change the code for controlling 4 motors 
 
Car listens to Ultrasound sensor to avoid obstacles after you click 'a' on keyboard. Or place your hand near the sensors. 
Placing hand near the sensor starts robotic movements.

## Known Issue:
Google is currently distributing preview version of Android things. Therefore you may find some missing features

Software Pulse Width Modulation (PWM) is currently not present for GPIO pins. At present Android things only supports Hardware PWM for Raspberry PI. SB Motor shield requires Software PWM for controlling motor speed therefore this function currently does not work and you can not change the speed of motors.
