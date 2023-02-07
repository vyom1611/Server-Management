import {Component, OnInit} from '@angular/core';
import {ServerService} from "./services/server.service";
import {BehaviorSubject, catchError, map, Observable, of, startWith} from "rxjs";
import {EApiResponse} from "./interfaces/ApiResponse";
import {DataState} from "./interfaces/state";
import {EDataState} from "./enums/data-state.enum";
import {EStatus} from "./enums/status.enum";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {



  // If something does not work later, try removing the undefined
  appState$!: Observable<DataState<EApiResponse>>;
  readonly Status = EStatus;
  readonly DataState = EDataState;
  private dataSubject = new BehaviorSubject<EApiResponse>(null);
  constructor(private serverService : ServerService) { }

  ngOnInit(): void {
    this.appState$ = this.serverService.servers$
    .pipe(
      map(response => {
        this.dataSubject.next(response);
        return { dataState: EDataState.LOADED_STATE, appData: { ...response, data: { servers: response.data.servers.reverse() } } }
      }),
      startWith({ dataState: EDataState.LOADING_STATE }),
      catchError((error: string) => {
        return of({ dataState: EDataState.ERROR_STATE, error });
      })
    );
  }

}
