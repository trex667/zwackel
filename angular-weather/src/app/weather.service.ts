import {Injectable} from "@angular/core";
import {Http} from "@angular/http";
import { Observable } from 'rxjs/Observable';
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

  getWeather(): Observable<string> {
    return this.http.get(this.openWeatherUrl)
      //.toPromise()
      .map(response => this.extractData(response));
      //.catch(this.handleError);
      // .map(res => res.json());
      // .subscribe()

  }

private extractData(res: any) {
    let body = res.json();
    return JSON.stringify(body);
  }

  private handleError(error: any) {
    console.error('An error occurred', error);
    return Observable.throw(error.message);
  }
}
