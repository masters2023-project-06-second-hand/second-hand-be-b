import React, { useState } from 'react';
import { styled } from 'styled-components';
import { Icon } from '@components/Icon/Icon';
import { StateBadge } from '@components/Badge/StateBadge';
import { formatPrice, displayTimeAgo, displayCount } from '@utils/index';
import { userInfoAtom } from '@atoms/userAtom';
import { useAtom } from 'jotai';
import { BottomMenu } from '@components/BottomMenu/BottomMenu';
import { getDropdownItems } from '@components/BottomMenu/getProductStatus';
import { usePageNavigator } from '@hooks/usePageNavigator';

type ProductItem = {
  id: number;
  writerId: number;
  thumbnailUrl: string;
  name: string;
  region: string;
  createdAt: string;
  status: string;
  price: number;
  likeCount: number;
  chattingCount: number;
};

export type ProductItemProps = {
  item: ProductItem;
};

export const ProductItem = React.forwardRef<HTMLLIElement, ProductItemProps>(
  ({ item }, ref) => {
    const [userInfo] = useAtom(userInfoAtom);
    const [menuVisible, setMenuVisible] = useState(false);
    const { navigateToDetail } = usePageNavigator();

    const isWriter = userInfo ? item.writerId === userInfo.id : false;

    return (
      <Wrapper ref={ref}>
        <Thumbnail
          onClick={() => navigateToDetail(item.id)}
          src={item.thumbnailUrl}
        />
        <Content>
          <Top>
            <Info>
              <Title onClick={() => navigateToDetail(item.id)}>
                {item.name}
              </Title>
              <RegionAndTime>
                {item.region} ・ {displayTimeAgo(item.createdAt)}
              </RegionAndTime>
              <StateAndPrice>
                {item.status !== '판매중' && <StateBadge state={item.status} />}
                <Price>{formatPrice(item.price)}</Price>
              </StateAndPrice>
            </Info>

            {isWriter && (
              <MoreButton onClick={() => setMenuVisible(!menuVisible)}>
                <Icon name="dots" size="M" stroke="neutralTextStrong" />
              </MoreButton>
            )}
            {menuVisible && (
              <BottomMenu
                productId={item.id}
                menuList={getDropdownItems(item.status)}
                onClose={() => setMenuVisible(!menuVisible)}
              />
            )}
          </Top>

          <ChatAndLike>
            {item.chattingCount > 0 && (
              <Chat>
                <Icon name="message" size="S" stroke="neutralTextWeak" />
                {displayCount(item.chattingCount)}
              </Chat>
            )}
            {item.likeCount > 0 && (
              <Like>
                <Icon name="heart" size="S" stroke="neutralTextWeak" />
                {displayCount(item.likeCount)}
              </Like>
            )}
          </ChatAndLike>
        </Content>
      </Wrapper>
    );
  }
);

const Wrapper = styled.li`
  padding: 16px 0;
  display: flex;
  width: 361px;
  gap: 16px;
  justify-content: center;
  border-bottom: 0.8px solid ${({ theme: { color } }) => color.neutralBorder};
`;
const Thumbnail = styled.img`
  width: 120px;
  height: 120px;
  background-size: cover;
  border-radius: ${({ theme: { radius } }) => radius.small};
  border: 1px solid ${({ theme: { color } }) => color.neutralBorder};
  cursor: pointer;
`;
const Content = styled.div`
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
`;
const Top = styled.div`
  display: flex;
  justify-content: space-between;
`;
const Info = styled.div`
  display: flex;
  flex-direction: column;
  gap: 4px;
`;
const Title = styled.h3`
  font: ${({ theme: { font } }) => font.displayDefault16};
  color: ${({ theme: { color } }) => color.neutralText};
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  max-width: 200px;
  cursor: pointer;
`;
const RegionAndTime = styled.p`
  font: ${({ theme: { font } }) => font.displayDefault12};
  color: ${({ theme: { color } }) => color.neutralTextWeak};
`;
const StateAndPrice = styled.div`
  display: flex;
  gap: 4px;
  align-items: center;
`;
const Price = styled.p`
  font: ${({ theme: { font } }) => font.displayStrong16};
  color: ${({ theme: { color } }) => color.neutralTextStrong};
`;
const MoreButton = styled.button`
  align-items: flex-start;
  padding: 0;
  height: 24px;
  z-index: 1;
`;
const ChatAndLike = styled.div`
  display: flex;
  gap: 4px;
  align-items: center;
  justify-content: flex-end;
  color: ${({ theme: { color } }) => color.neutralTextWeak};
  font: ${({ theme: { font } }) => font.displayDefault12};
`;
const Chat = styled.div`
  display: flex;
  align-items: center;
`;
const Like = styled.div`
  display: flex;
  align-items: center;
`;
