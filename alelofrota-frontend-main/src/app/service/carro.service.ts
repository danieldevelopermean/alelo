import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Carro } from './../model/carro.model';
import { HELP_DESK_API } from "./helpdesk.api";

@Injectable({
  providedIn: 'root'
})
export class CarroService {

  private listaCarros: any[];
  private url: string = "http://localhost:8080/alelofrota/vehicle";

  constructor(private httpClient: HttpClient) {
    this.listaCarros = [];
  }

  carros(): Observable<Carro[]>{
    this.listaCarros = [];

    return this.httpClient.get<Carro[]>(HELP_DESK_API);
  }

  carroFindById(id:string): Observable<any>{
    return this.httpClient.get<Carro[]>(`${HELP_DESK_API}/${id}`);
  }

  adicionar(carro: Carro): Observable<Carro>{
    return this.httpClient.post<Carro>(HELP_DESK_API, carro);
  }

  buscarPorPlaca(placa: string): Observable<Carro[]>{
    this.listaCarros = [];

    return this.httpClient.get<Carro[]>(`${HELP_DESK_API}/search/${placa}`);
  }

  atualizar(carro: Carro, id:string): Observable<Carro>{
    return this.httpClient.put<Carro>(`${HELP_DESK_API}/${id}`, carro);
  }

  carroDelete(id:string): Observable<Carro[]>{
    return this.httpClient.delete<Carro[]>(`${HELP_DESK_API}/${id}`);
  }
}
