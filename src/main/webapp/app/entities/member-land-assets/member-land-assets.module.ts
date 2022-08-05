import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { MemberLandAssetsComponent } from './list/member-land-assets.component';
import { MemberLandAssetsDetailComponent } from './detail/member-land-assets-detail.component';
import { MemberLandAssetsUpdateComponent } from './update/member-land-assets-update.component';
import { MemberLandAssetsDeleteDialogComponent } from './delete/member-land-assets-delete-dialog.component';
import { MemberLandAssetsRoutingModule } from './route/member-land-assets-routing.module';

@NgModule({
  imports: [SharedModule, MemberLandAssetsRoutingModule],
  declarations: [
    MemberLandAssetsComponent,
    MemberLandAssetsDetailComponent,
    MemberLandAssetsUpdateComponent,
    MemberLandAssetsDeleteDialogComponent,
  ],
})
export class MemberLandAssetsModule {}
