import { atom } from 'jotai';
import { DEFAULT_REGIONS } from '@constants/constants';

type UserInfo = {
  id: number;
  nickname: string;
  profileImg: string;
} | null;

interface Region {
  id: number;
  name: string;
}

export const userInfoAtom = atom<UserInfo>(null);

export type UserRegions = {
  selectedRegion: { id: number; name: string };
  regions: Region[];
};

export const userRegionsAtom = atom<UserRegions>({
  selectedRegion: {
    id: DEFAULT_REGIONS.id,
    name: DEFAULT_REGIONS.name,
  },
  regions: [DEFAULT_REGIONS],
});
