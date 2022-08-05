import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { MemberAssetsComponent } from './list/member-assets.component';
import { MemberAssetsDetailComponent } from './detail/member-assets-detail.component';
import { MemberAssetsUpdateComponent } from './update/member-assets-update.component';
import { MemberAssetsDeleteDialogComponent } from './delete/member-assets-delete-dialog.component';
import { MemberAssetsRoutingModule } from './route/member-assets-routing.module';

@NgModule({
  imports: [SharedModule, MemberAssetsRoutingModule],
  declarations: [MemberAssetsComponent, MemberAssetsDetailComponent, MemberAssetsUpdateComponent, MemberAssetsDeleteDialogComponent],
})
export class MemberAssetsModule {}
