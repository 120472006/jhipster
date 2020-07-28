import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared/shared.module';
import { RoleComponent } from './role.component';
import { RoleDetailComponent } from './role-detail.component';
import { roleRoute } from './role.route';

@NgModule({
  imports: [GatewaySharedModule, RouterModule.forChild(roleRoute)],
  declarations: [RoleComponent, RoleDetailComponent],
})
export class Service1RoleModule {}
