export default function extractRegionName(regionName: string): string {
  return regionName.split(' ').pop() || '지역을 확인할 수 없습니다';
}
