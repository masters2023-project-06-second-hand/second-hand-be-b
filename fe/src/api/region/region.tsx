import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { useAtom, useSetAtom } from 'jotai';
import { userInfoAtom, userRegionsAtom } from '@atoms/userAtom';
import { privateApi } from '../index';
import { API_ENDPOINTS } from '@constants/endpoints';
import { QUERY_KEYS } from '@api/queryKey';

/* TODO. 코드 분리 하자 */

interface Region {
  id: number;
  name: string;
}

interface UserRegions {
  selectedRegionId: number;
  regions: Region[];
}

export const getUserReigions = async (memberId: number) => {
  const { data } = await privateApi.get(API_ENDPOINTS.USER_REGIONS(memberId));
  return data;
};

export const deleteUserRegion = async (userId: number, regionId: number) => {
  return privateApi.delete(API_ENDPOINTS.USER_REGIONS(userId), {
    data: { id: regionId },
  });
};

export const setUserRegion = async (userId: number, regionId: number) => {
  return privateApi.put(API_ENDPOINTS.USER_REGIONS(userId), { id: regionId });
};

export const addUserRegion = async (userId: number, regionId: number) => {
  return privateApi.post(API_ENDPOINTS.USER_REGIONS(userId), { id: regionId });
};

export const useGetUserReigions = (): void => {
  const [userInfo] = useAtom(userInfoAtom);
  const setUserRegions = useSetAtom(userRegionsAtom);

  useQuery<UserRegions>(
    [QUERY_KEYS.USER_REGIONS(userInfo ? userInfo.id : null)],
    () => {
      if (!userInfo) {
        throw new Error('User not logged in');
      }
      return getUserReigions(userInfo.id);
    },
    {
      enabled: !!userInfo,
      onSuccess: (data) => {
        const selectedRegion = data.regions.find(
          (r) => r.id === data.selectedRegionId
        );
        if (!selectedRegion) {
          throw new Error('Selected region not found');
        }

        setUserRegions({
          selectedRegion,
          regions: data.regions,
        });
      },
    }
  );
};

export const useDeleteUserRegionMutation = () => {
  const [userInfo] = useAtom(userInfoAtom);
  const queryClient = useQueryClient();

  const { mutate } = useMutation(
    (regionId: number) => {
      if (!userInfo) {
        throw new Error('User not logged in');
      }
      return deleteUserRegion(userInfo.id, regionId);
    },
    {
      onSuccess: () => {
        queryClient.invalidateQueries([
          QUERY_KEYS.USER_REGIONS(userInfo ? userInfo.id : null),
        ]);
        /* + add toast */
      },
    }
  );

  return mutate;
};

export const useSetUserRegionMutation = () => {
  const [userInfo] = useAtom(userInfoAtom);
  const queryClient = useQueryClient();

  const { mutate } = useMutation(
    (regionId: number) => {
      if (!userInfo) {
        throw new Error('User not logged in');
      }
      return setUserRegion(userInfo.id, regionId);
    },
    {
      onSuccess: () => {
        queryClient.invalidateQueries([
          QUERY_KEYS.USER_REGIONS(userInfo ? userInfo.id : null),
        ]);
        /* + add toast */
      },
    }
  );

  return mutate;
};

export const useAddUserRegionMutation = () => {
  const [userInfo] = useAtom(userInfoAtom);
  const queryClient = useQueryClient();

  const { mutate } = useMutation(
    (regionId: number) => {
      if (!userInfo) {
        throw new Error('User not logged in');
      }
      return addUserRegion(userInfo.id, regionId);
    },
    {
      onSuccess: () => {
        queryClient.invalidateQueries([
          QUERY_KEYS.USER_REGIONS(userInfo ? userInfo.id : null),
        ]);
        /* + add toast */
      },
    }
  );

  return mutate;
};
