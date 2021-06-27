import { ListaCarrosComponent } from './listagem-carros/lista-carros.component';
import { NovoCarroComponent } from './novo-carro/novo-carro.component';
import { NgModule } from "@angular/core";
import { RouterModule } from "@angular/router";

import { Routes } from '@angular/router';

export const routes: Routes = [
  {path: '', redirectTo: 'lista-carros', pathMatch: 'full'},
  {path: 'novo-carro', component: NovoCarroComponent},
  {path: 'novo-carro/:id', component: NovoCarroComponent},
  {path: 'lista-carros', component: ListaCarrosComponent}
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
  }
)
export class AppRountigModule{}
