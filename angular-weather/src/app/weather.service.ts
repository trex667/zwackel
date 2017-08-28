import {Injectable} from "@angular/core";
import {Http} from "@angular/http";
import "rxjs/add/operator/toPromise";
import "rxjs/add/operator/map";

@Injectable()
export class WeatherService {

  private openWeatherUrl = "http://api.openweathermap.org/data/2.5/forecast?APPID=a59c9a186c9eddca27a2f6f157d45275&q=trier,de&mode=json";

  constructor(private http: Http) {
  }

  getInfo(): String {
    return "********************** WeatherService Info *********************";
  }

  getWeather(): Promise<string> {
    return this.http.get(this.openWeatherUrl)
      .toPromise()
      .then(response => response.json().data as string)
      .catch(this.handleError);
      // .map(res => res.json());
      // .subscribe()

  }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error);
    return Promise.reject(error.message || error);
  }
}
