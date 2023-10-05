import {
  RegionSettingModalProps,
  SearchRegionModalProps,
  CategoryModalProps,
  AlertModalProps,
  ModalType,
  ModalPropsMapping,
} from './types';

import { AlertModal } from './Modal';
import { RegionSettingModal } from '@components/Modal/RegionSettingModal';
import { SearchRegionModal } from '@components/Modal/SearchRegionModal';
import { CategoryModal } from './CategoryModal';

export function createModalComponent<T extends ModalType>(
  type: T,
  props: ModalPropsMapping[T]
): JSX.Element {
  switch (type) {
    case 'regionSetting':
      return <RegionSettingModal {...(props as RegionSettingModalProps)} />;
    case 'searchRegion':
      return <SearchRegionModal {...(props as SearchRegionModalProps)} />;
    case 'category':
      return <CategoryModal {...(props as CategoryModalProps)} />;
    case 'alert':
      return <AlertModal {...(props as AlertModalProps)} />;
    default:
      throw new Error(`Unsupported modal type: ${type}`);
  }
}
