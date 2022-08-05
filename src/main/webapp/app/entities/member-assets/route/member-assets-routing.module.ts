import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MemberAssetsComponent } from '../list/member-assets.component';
import { MemberAssetsDetailComponent } from '../detail/member-assets-detail.component';
import { MemberAssetsUpdateComponent } from '../update/member-assets-update.component';
import { MemberAssetsRoutingResolveService } from './member-assets-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const memberAssetsRoute: Routes = [
  {
    path: '',
    component: MemberAssetsComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MemberAssetsDetailComponent,
    resolve: {
      memberAssets: MemberAssetsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MemberAssetsUpdateComponent,
    resolve: {
      memberAssets: MemberAssetsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MemberAssetsUpdateComponent,
    resolve: {
      memberAssets: MemberAssetsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(memberAssetsRoute)],
  exports: [RouterModule],
})
export class MemberAssetsRoutingModule {}
