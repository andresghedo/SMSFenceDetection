# SMS Fence Detection
<ul>
  <li>Mobile Systems | accademic project @ UniBo - Alma Mater Studiorum
  <li>Android App that allows you to create fences, selecting a center and a range, and capture all ENTER/EXIT events. 
  <li>Whenever app detect any of these events, it is automatically sent an alert SMS to a mobile phone.
  <li>[See Report Directory for details]
</ul>
<h2>Project Goal:</h2>
The main goal of the project, however, is to compare the battery
consumption of two strategies/services for location monitoring in
time.
The first strategy is based on the simple polling strategy, while
the second adopt a smarter auto-adaptive approach to avoid
an excessive battery consumption, which would make the app
unusable.
<h3>Polling Strategy - Exaggerated battery consumption</h3>
<ul>
  <li>looking for position every 5 seconds
  <li>Priority: PRIORITY_HIGH_ACCURACY
  <li>GPS Sensor works for all polling period
</ul>
<h3>Auto-adaptive approach - Low battery consumption</h3>
<ul>
  <li>IDEA: Expensive resources(as GPS Sensor) only when absolutely necessary!
  <li>Tha apprach is based on 3 parameters:
    <ul>
      <li>Distance fences-current position
      <li>Direction
      <li>Speed
    </ul>
  </li>
</ul>
<p align="left">
  <img src="/img/coeff.png" width="50"/>
</p>
<p align="left">
  <img src="/img/formula.png" width="550"/>
</p>
