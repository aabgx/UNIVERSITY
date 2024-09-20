export interface Menu {
  code: number;
  name: string;
}

//cache menu
export const cacheMenu = async (menu: Menu[]) => {
  localStorage.setItem(`menu`, JSON.stringify(menu));
};
export const getCachedMenu = (): Menu[] => {
  const menu = localStorage.getItem(`menu`);
  if (menu === null) {
    return [];
  }
  return JSON.parse(menu);
};
