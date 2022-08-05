import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { MemberBankComponent } from './list/member-bank.component';
import { MemberBankDetailComponent } from './detail/member-bank-detail.component';
import { MemberBankUpdateComponent } from './update/member-bank-update.component';
import { MemberBankDeleteDialogComponent } from './delete/member-bank-delete-dialog.component';
import { MemberBankRoutingModule } from './route/member-bank-routing.module';

@NgModule({
  imports: [SharedModule, MemberBankRoutingModule],
  declarations: [MemberBankComponent, MemberBankDetailComponent, MemberBankUpdateComponent, MemberBankDeleteDialogComponent],
})
export class MemberBankModule {}
