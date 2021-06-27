import { Component, Input } from "@angular/core";
import {ConfirmationService, ConfirmEventType, MessageService} from 'primeng/api';
import { CarroService } from "../service/carro.service";
import { Router } from '@angular/router';

import { Carro } from './../model/carro.model';

@Component({
  selector: 'listagem-carros',
  templateUrl: './lista-carros.component.html',
  styleUrls: ['./lista-carros.component.scss'],
  providers: [ConfirmationService,MessageService]
})
export class ListaCarrosComponent {
  first = 0;
  rows = 10;
  position: string;
  placa: string;

  constructor(
    private service: CarroService,
    private confirmationService: ConfirmationService,
    private messageService: MessageService,
    private router: Router
  ) { }

  listaCarros: any[];

  ngOnInit() {
    this.listaCarros = [];

    this.service
      .carros().subscribe((carros: Carro[]) => this.listaCarros = carros);

  }

  carroFindById(id: string) {
    this.router.navigate(['novo-carro', id]);
  }

  carroDelete(id: string, placa: string) {
    console.log(id);

    this.confirmationService.confirm({
      message: 'Deseja excluir o veículo placa ' + placa + '?' ,
      header: 'Excluir',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.service
        .carroDelete(id).subscribe(resultado => this.ngOnInit());
        this.messageService.add({severity:'info', summary:'Confirmado', detail:'Você confirmou'});
      },
      reject: (type) => {
          switch(type) {
              case ConfirmEventType.REJECT:
                  this.messageService.add({severity:'error', summary:'Rejeitado', detail:'Você rejeitou'});
              break;
              case ConfirmEventType.CANCEL:
                  this.messageService.add({severity:'warn', summary:'Cancelado', detail:'Você cancelou'});
              break;
          }
      }
    });
  }

  buscarPorPlaca() {
    console.log('teste');
    this.service.buscarPorPlaca(this.placa).subscribe((resultado: Carro[]) => {
      this.listaCarros = [];
      this.listaCarros = resultado;
    }, (error) => console.error(error));
  }

  next() {
    this.first = this.first + this.rows;
  }

  prev() {
    this.first = this.first - this.rows;
  }

  reset() {
    this.first = 0;
  }

  isLastPage(): boolean {
    return this.listaCarros ? this.first === (this.listaCarros.length - this.rows) : true;
  }

  isFirstPage(): boolean {
    return this.listaCarros ? this.first === 0 : true;
  }
}

