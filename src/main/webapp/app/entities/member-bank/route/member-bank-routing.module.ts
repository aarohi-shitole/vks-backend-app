import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MemberBankComponent } from '../list/member-bank.component';
import { MemberBankDetailComponent } from '../detail/member-bank-detail.component';
import { MemberBankUpdateComponent } from '../update/member-bank-update.component';
import { MemberBankRoutingResolveService } from './member-bank-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const memberBankRoute: Routes = [
  {
    path: '',
    component: MemberBankComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MemberBankDetailComponent,
    resolve: {
      memberBank: MemberBankRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MemberBankUpdateComponent,
    resolve: {
      memberBank: MemberBankRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MemberBankUpdateComponent,
    resolve: {
      memberBank: MemberBankRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(memberBankRoute)],
  exports: [RouterModule],
})
export class MemberBankRoutingModule {}
