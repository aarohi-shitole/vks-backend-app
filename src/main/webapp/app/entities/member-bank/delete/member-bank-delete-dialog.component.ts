import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IMemberBank } from '../member-bank.model';
import { MemberBankService } from '../service/member-bank.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './member-bank-delete-dialog.component.html',
})
export class MemberBankDeleteDialogComponent {
  memberBank?: IMemberBank;

  constructor(protected memberBankService: MemberBankService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.memberBankService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
