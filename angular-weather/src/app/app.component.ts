import {Component, Inject, OnInit} from '@angular/core';
import {WeatherService} from "./weather.service";
import {Weather} from "./weather";

@Component({
  selector: 'app-root',
  providers: [WeatherService],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  title = 'open weather app';
  data: Weather[];
  errorMessage: string;

  // private weatherService :WeatherService;

  constructor(private weatherService :WeatherService){
    // json = weatherService.getWeather();
    weatherService.getWeatherForecast("trier").subscribe(data => {this.data = data},
                    error =>  this.errorMessage = <any>error,
     );
    console.log("Huhu: " + this.data);
  }

}


