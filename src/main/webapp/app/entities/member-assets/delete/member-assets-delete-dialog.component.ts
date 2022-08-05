import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IMemberAssets } from '../member-assets.model';
import { MemberAssetsService } from '../service/member-assets.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './member-assets-delete-dialog.component.html',
})
export class MemberAssetsDeleteDialogComponent {
  memberAssets?: IMemberAssets;

  constructor(protected memberAssetsService: MemberAssetsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.memberAssetsService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
