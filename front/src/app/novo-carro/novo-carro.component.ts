import { Carro } from './../model/carro.model';
import { Component, Output } from "@angular/core";
import { EventEmitter } from '@angular/core';
import { CarroService } from "../service/carro.service";
import { Router, ActivatedRoute } from '@angular/router';
import {Message,MessageService} from 'primeng/api';

@Component({
  selector: 'novo-carro',
  templateUrl: './novo-carro.component.html',
  styleUrls: ['./novo-carro.component.scss'],
  providers: [MessageService]
})
export class NovoCarroComponent {

  title: string = "Update Vehicle";

  placa: string;
  modelo: string;
  marca: string;
  cor: string;
  status: string;
  id: string;
  disable: boolean = false;
  msgErro: string;
  msgSucess: string;

  constructor(private service: CarroService, private router: Router, private route: ActivatedRoute, private messageService: MessageService) {
    this.route.params.subscribe(params => this.id = params['id']);
  }

  @Output() aoSalvar = new EventEmitter<any>();

  ngOnInit() {

    if(this.id != undefined){
      this.title = "Editar Veículo";
      this.disable = true;

      this.service
        .carroFindById(this.id).subscribe(resultado => {
          this.placa = resultado.placa;
          this.modelo = resultado.modelo;
          this.marca = resultado.marca;
          this.cor = resultado.cor;
          this.status = resultado.status;
      });
    }
  }

  showViaService(message: string) {
    this.messageService.clear();
    this.messageService.add({severity:'success', summary:'Sucesso', detail: message});
  }

  novoCarro() {
    const valorEmitir: Carro = {placa: this.placa, modelo: this.modelo, marca: this.marca, cor: this.cor, status: this.status};

    if(this.id == undefined){
      this.service.adicionar(valorEmitir).subscribe(resultado => {
        this.limparCampos();

        this.msgSucess = 'Veículo adicionado com sucesso.';
        this.showViaService(this.msgSucess);

        setTimeout(() => { this.router.navigateByUrl('lista-carros'); }, 1000);

      }, (e) => {
        this.messageService.clear();
        this.msgErro = e.error.message;
        this.messageService.add({severity:'error', summary:'Erro', detail: this.msgErro});

        this.placa = valorEmitir.placa;
        this.modelo = valorEmitir.modelo;
        this.marca = valorEmitir.marca;
        this.cor = valorEmitir.cor;
      });
    }else{
      this.service.atualizar(valorEmitir, this.id).subscribe(resultado => {
        this.limparCampos();

        this.msgSucess = 'Veículo atualizado com sucesso.';
        this.showViaService(this.msgSucess);

        setTimeout(() => { this.router.navigateByUrl('lista-carros'); }, 1000);
      }, (e) => {
        this.messageService.clear();
        this.msgErro = e.error.message;
        this.messageService.add({severity:'error', summary:'Erro', detail: this.msgErro});
      });
    }

    this.limparCampos();

  }

  limparCampos(){
    this.placa = '';
    this.modelo = '';
    this.marca = '';
    this.cor = '';
  }
}
