import {Injectable} from "@angular/core";
import {Http} from "@angular/http";
import {Observable} from 'rxjs/Observable';
import "rxjs/add/operator/toPromise";
import "rxjs/add/operator/map";
import {Weather} from "../weather";

@Injectable()
export class WeatherService {

  private API_ID = "a59c9a186c9eddca27a2f6f157d45275";
  private openWeatherUrl = "http://api.openweathermap.org/data/2.5/forecast?units=metric&appid=" + this.API_ID;

  constructor(private http: Http) {
  }


  public getWeatherForecast(cityName): Observable<Weather[]>{
    return this.http.get(this.openWeatherUrl +'&q='+ cityName)
      .map(response => this.extractData(cityName, response));
  }

  private extractData(cityName: string, res: any) {
    let body = res.json();
    let weather = [];
    for(let element of body.list){
      console.log(element);
      weather.push(new Weather(cityName, element.main.temp, element.dt_txt))

    }
    return weather || { };
  }

  private handleError (error: any) {
    let errMsg: string;
    errMsg = error.message ? error.message : error.toString();
    console.error(errMsg);
    return Observable.throw(errMsg);
  }
}
