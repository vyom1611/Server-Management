import {Component, OnInit} from '@angular/core';
import {ServerService} from "./services/server.service";
import {BehaviorSubject, catchError, map, Observable, of, pipe, startWith} from "rxjs";
import {EApiResponse} from "./interfaces/ApiResponse";
import {DataState} from "./interfaces/state";
import {EDataState} from "./enums/data-state.enum";
import {EStatus} from "./enums/status.enum";
import {NgForm} from "@angular/forms";
import {Server} from "./interfaces/server";
import * as repl from "repl";

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
  private filterSubject = new BehaviorSubject<String>("");
  filterStatus$ = this.filterSubject.asObservable();
  private dataSubject = new BehaviorSubject<EApiResponse>(null);
  private isLoading = new BehaviorSubject<boolean>(false);
  isLoading$ = this.isLoading.asObservable();

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

  pingServer(ipAddress: string): void {
    this.filterSubject.next(ipAddress);
    this.appState$ = this.serverService.ping$(ipAddress)
    .pipe(
      map(response => {
        const index = this.dataSubject.value.data.servers.findIndex(server =>
          server.id === response.data.server.id);
        this.dataSubject.value.data.servers[index] = response.data.server;
        this.filterSubject.next('');
        return { dataState: EDataState.LOADED_STATE, appData: this.dataSubject.value }
      }),
      startWith({ dataState: EDataState.LOADED_STATE, appData: this.dataSubject.value }),
      catchError((error: string) => {
        this.filterSubject.next('');
        return of({ dataState: EDataState.ERROR_STATE, error });
      })
    );
  }

  filterServers(status: EStatus): void {
    this.appState$ = this.serverService.filter$(status, this.dataSubject.value)
    .pipe(
      map(response => {
        return { dataState: EDataState.LOADED_STATE, appData: response }
      }),
      startWith({ dataState: EDataState.LOADED_STATE, appData: this.dataSubject.value }),
      catchError((error: string) => {
        return of({ dataState: EDataState.ERROR_STATE, error });
      })
    );
  }

  saveServer(serverForm: NgForm): void {
    this.isLoading.next(true);
    this.appState$ = this.serverService.save$(serverForm.value as Server)
    .pipe(
      map(response => {
        this.dataSubject.next(
          {...response, data: {servers: [response.data.server, ...this.dataSubject.value.data.servers]}}
        );
        document.getElementById('closeModal').click();
        this.isLoading.next(false);
        serverForm.resetForm({ status: this.Status.SERVER_DOWN })
        return { dataState: EDataState.LOADED_STATE, appData: response }
      }),
      startWith({ dataState: EDataState.LOADED_STATE, appData: this.dataSubject.value }),
      catchError((error: string) => {
        this.isLoading.next(false);
        return of({ dataState: EDataState.ERROR_STATE, error });
      })
    );
  }

}
