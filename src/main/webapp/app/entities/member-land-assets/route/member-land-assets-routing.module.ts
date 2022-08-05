import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MemberLandAssetsComponent } from '../list/member-land-assets.component';
import { MemberLandAssetsDetailComponent } from '../detail/member-land-assets-detail.component';
import { MemberLandAssetsUpdateComponent } from '../update/member-land-assets-update.component';
import { MemberLandAssetsRoutingResolveService } from './member-land-assets-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const memberLandAssetsRoute: Routes = [
  {
    path: '',
    component: MemberLandAssetsComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MemberLandAssetsDetailComponent,
    resolve: {
      memberLandAssets: MemberLandAssetsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MemberLandAssetsUpdateComponent,
    resolve: {
      memberLandAssets: MemberLandAssetsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MemberLandAssetsUpdateComponent,
    resolve: {
      memberLandAssets: MemberLandAssetsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(memberLandAssetsRoute)],
  exports: [RouterModule],
})
export class MemberLandAssetsRoutingModule {}
