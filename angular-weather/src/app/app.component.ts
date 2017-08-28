import {Component, Inject, OnInit} from '@angular/core';
import {WeatherService} from "./weather.service";

@Component({
  selector: 'app-root',
  providers: [WeatherService],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  title = 'weather app';
  data : string;

  // private weatherService :WeatherService;

  constructor(private weatherService :WeatherService){
    // json = weatherService.getWeather();
    weatherService.getWeather().then(res => this.data);
    console.log("Huhu: " + this.data);
  }

}
