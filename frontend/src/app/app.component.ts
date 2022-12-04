import {Component, OnInit} from '@angular/core';
import {ServerService} from "./services/server.service";
import {catchError, map, Observable, of, startWith} from "rxjs";
import {EApiResponse} from "./interfaces/ApiResponse";
import {DataState} from "./interfaces/state";
import {EDataState} from "./enums/data-state.enum";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {



  // If something does not work later, try removing the undefined
  appState$!: Observable<DataState<EApiResponse>>;
  rowData: String = 'Hu';
  columnsToDisplay: String[] = ['Image', 'Ip-Address', 'Name','Memory', 'Type', 'Status', 'Ping', 'Edit'];

  constructor(private serverService : ServerService) { }

  ngOnInit(): void {
    this.appState$ = this.serverService.servers$
    .pipe(
      map(response => { return { dataState: EDataState.LOADED_STATE, appData : response }}),
      startWith({ dataState: EDataState.LOADING_STATE}),
      catchError((error: string) => {
        return of({ dataState: EDataState.ERROR_STATE, error: error})
      })
    )
  }

}
