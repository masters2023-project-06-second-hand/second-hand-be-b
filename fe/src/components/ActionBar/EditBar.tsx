import { UserRegions } from '@atoms/userAtom';
import { TextButton } from '@components/Button/TextButton';
import { MenuDropdown } from '@components/Dropdown/MenuDropdown';
import { Icon } from '@components/Icon/Icon';
import extractRegionName from '@utils/extractRegionName';
import { styled } from 'styled-components';

type Props = {
  userRegions: UserRegions;
  selectRegion(regionId: number): void;
};

export const EditBar: React.FC<Props> = ({ userRegions, selectRegion }) => {
  return (
    <Bar>
      <MenuDropdown
        trigger={
          <RegionButton onClick={() => {}}>
            <Icon name="mapPinFilled" fill="neutralTextStrong" />
            <Region>
              {extractRegionName(userRegions.selectedRegion.name)}
            </Region>
          </RegionButton>
        }
        position="top-left"
      >
        {userRegions.regions.map((region) => (
          <RegionList
            key={region.id}
            onClick={() => selectRegion(region.id)}
            $isSelected={userRegions.selectedRegion.id === region.id}
          >
            {extractRegionName(region.name)}
          </RegionList>
        ))}
      </MenuDropdown>
    </Bar>
  );
};

const Bar = styled.div`
  display: flex;
  width: 100%;
  height: 100%;
`;

const RegionButton = styled(TextButton)`
  padding: 0;
  height: 100%;
  user-select: none;
`;

const Region = styled.span`
  color: ${({ theme }) => theme.color.neutralTextStrong};
  font: ${({ theme }) => theme.font.availableDefault16};
`;

const RegionList = styled.li<{ $isSelected: boolean }>`
  font: ${({ theme: { font } }) => font.displayStrong16};
`;
