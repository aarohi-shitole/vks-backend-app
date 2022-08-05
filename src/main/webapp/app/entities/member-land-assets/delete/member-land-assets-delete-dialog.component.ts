import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IMemberLandAssets } from '../member-land-assets.model';
import { MemberLandAssetsService } from '../service/member-land-assets.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './member-land-assets-delete-dialog.component.html',
})
export class MemberLandAssetsDeleteDialogComponent {
  memberLandAssets?: IMemberLandAssets;

  constructor(protected memberLandAssetsService: MemberLandAssetsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.memberLandAssetsService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
