import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Carro } from './../model/carro.model';
import { urlAPi } from "./urlApi";

@Injectable({
  providedIn: 'root'
})
export class CarroService {

  private listaCarros: any[];

  constructor(private httpClient: HttpClient) {
    this.listaCarros = [];
  }

  carros(): Observable<Carro[]>{
    this.listaCarros = [];

    return this.httpClient.get<Carro[]>(urlAPi);
  }

  carroFindById(id:string): Observable<any>{
    return this.httpClient.get<Carro[]>(`${urlAPi}/${id}`);
  }

  adicionar(carro: Carro): Observable<Carro>{
    return this.httpClient.post<Carro>(urlAPi, carro);
  }

  buscarPorPlaca(identificacao: string): Observable<Carro[]>{
    this.listaCarros = [];

    return this.httpClient.get<Carro[]>(`${urlAPi}/search/${identificacao}`);
  }

  atualizar(carro: Carro, id:string): Observable<Carro>{
    return this.httpClient.put<Carro>(`${urlAPi}/${id}`, carro);
  }

  carroDelete(id:string): Observable<Carro[]>{
    return this.httpClient.delete<Carro[]>(`${urlAPi}/${id}`);
  }
}
