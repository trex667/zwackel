import {Component, Inject, OnInit} from '@angular/core';
import {WeatherService} from "./weather/weather.service";
import {Weather} from "./weather";

@Component({
  selector: 'app-root',
  providers: [WeatherService],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  title = 'open weather app';
  agfaDevSides: Map<string, Weather[]>;
  errorMessage: string;

  // private weatherService :WeatherService;

  constructor(private weatherService :WeatherService){
    this.agfaDevSides = new Map();
    // json = weatherService.getWeather();
    weatherService.getWeatherForecast("trier").subscribe(data => {this.agfaDevSides.set("trier", data)},
                    error =>  this.errorMessage = <any>error,
     );
    weatherService.getWeatherForecast("rottenburg").subscribe(data => {this.agfaDevSides.set("rottenburg", data)},
      error =>  this.errorMessage = <any>error,
    );
    weatherService.getWeatherForecast("wien").subscribe(data => {this.agfaDevSides.set("wien", data)},
      error =>  this.errorMessage = <any>error,
    );
    weatherService.getWeatherForecast("gent").subscribe(data => {this.agfaDevSides.set("gent", data)},
      error =>  this.errorMessage = <any>error,
    );
    weatherService.getWeatherForecast("bordeaux").subscribe(data => {this.agfaDevSides.set("bordeaux", data)},
      error =>  this.errorMessage = <any>error,
    );
    console.log("Huhu: " + this.agfaDevSides);
  }

}


