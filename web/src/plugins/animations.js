import { gsap } from "gsap";

/**
 * 要素にバウンスアニメーション効果を適用する関数
 * 
 * @param {HTMLElement|string} target - アニメーションの対象（セレクタ or 要素）
 * @param {Number} duration - アニメーションの持続時間（秒）
 * @returns {gsap.core.Tween} GSAPのTweenインスタンス
 */
export const animationBounce = (target, duration = 1) => {
  return gsap.fromTo(
    target,
    {
      // 初期状態：透明で初期位置（y=0）
      opacity: 0,
      y: 0
    },
    {
      // アニメーション状態：上方向に移動し、完全に表示
      y: -50,           // 上に50ピクセル移動
      opacity: 1,       // 完全に表示
      duration,         // アニメーション時間
      ease: "back.out", // はずみ返る効果のイージング
      repeat: 1,        // 1回繰り返す（合計2回再生）
      yoyo: true,       // 繰り返し時に逆再生する
    }
  );
};

/**
 * 要素をフェードインさせながら指定方向から表示するアニメーション関数
 * 
 * @param {Element|String} target - アニメーション対象
 * @param {String} direction - アニメーションの開始方向 ('top', 'bottom', 'left', 'right')
 * @param {Number} duration - アニメーションの持続時間（秒）
 * @param {Number} offset - 開始位置のオフセット（ピクセル）
 * @returns {gsap.core.Tween} GSAPのTweenインスタンス
 */
export const animateFadeInFrom = (target, direction = 'bottom', duration = 1, offset = 200) => {
  // 移動の初期座標を設定
  let x = 0;
  let y = 0;

  // 方向に応じて適切な座標を設定
  switch (direction) {
    case 'top':
      y = -offset; // 上から下へ
      break;
    case 'bottom':
      y = offset;  // 下から上へ
      break;
    case 'left':
      x = -offset; // 左から右へ
      break;
    case 'right':
      x = offset;  // 右から左へ
      break;
    default:
      // 無効な方向が指定された場合は警告を表示
      console.warn(`Invalid direction "${direction}" passed to animateFadeInFrom`);
  }

  // GSAPを使用してアニメーションを作成
  return gsap.fromTo(
    target,
    {
      // 初期状態：透明で指定方向にオフセットされた位置
      opacity: 0,
      x,
      y,
    },
    {
      // 最終状態：完全に表示され、本来の位置に配置
      opacity: 1,
      x: 0,
      y: 0,
      duration,  // アニメーション時間
      ease: "power2.out",  // イージング関数（徐々に減速）
    }
  );
};


/**
 * しっぽを振るアニメーション関数
 *
 * @param {HTMLElement|string} target - アニメーション対象
 * @param {number} repeat - 繰り返し回数（-1で無限ループ）
 */
export const animateTailShake = (target, repeat = -1) => {
  console.log('animateTailShake target', target);
  
  // 無限ループ用の設定
  if (repeat === -1) {
    return gsap.fromTo(
      target,
      {
        rotation: -10,
        transformOrigin: "center",
      },
      {
        rotation: 10,
        duration: 0.2,
        yoyo: true,
        repeat: -1, // 明示的に-1を指定
        ease: "sine.inOut",
        repeatDelay: 0.1,
      }
    );
  } 
  // 通常の繰り返し用設定
  else {
    return gsap.fromTo(
      target,
      {
        rotation: -10,
        transformOrigin: "center",
      },
      {
        rotation: 10,
        duration: 0.2,
        yoyo: true,
        repeat: repeat,
        ease: "sine.inOut",
      }
    );
  }
};

/**
 * 任意のターゲットを回転させる共通アニメーション関数
 * @param {string | Element} target - アニメーション対象
 * @param {number} [rotation=360] - 回転量（デフォルト360度）
 */
export const rotateElement = (target, rotation = 360) => {

  gsap.killTweensOf(target); // 既存アニメーションを停止

  gsap.to(target, {
    rotation: `+=${rotation}`,
    duration: 1,
    ease: "power1.inOut",
    transformOrigin: "center center",
  });
};


/**
 * 任意のターゲットを、画面外から画面外へ流すアニメーション関数
 * 
 * @param {HTMLElement|string} target - アニメーションさせる要素（セレクタ or DOM）
 * @param {string} direction - アニメーションの方向 ('top' | 'bottom' | 'left' | 'right')
 * @param {number} duration - アニメーションの所要時間（秒）
 * @returns {GSAPTween} GSAPのトゥイーンオブジェクト
 */
// 指定方向に要素を画面外へ移動させるアニメーション
export const animateFlyAcrossScreen = (target, direction = 'top', duration = 0.8) => {
  const el = document.querySelector(target);
  if (!el) {
    console.warn(`Target not found: ${target}`);
    return;
  }

  // 要素を表示
  el.style.visibility = 'visible';

  // アニメーション設定
  const directions = {
    top:    { from: { y: '-100px' }, to: { y: '100vh' } },
    bottom: { from: { y: '100px' }, to: { y: '-100vh' } },
    left:   { from: { x: '-100px' }, to: { x: '100vw' } },
    right:  { from: { x: '100px' }, to: { x: '-100vw' } },
  };

  const config = directions[direction];
  if (!config) {
    console.warn(`Unknown direction: ${direction}`);
    return;
  }

  // 初期位置を設定
  gsap.set(el, config.from);

  // アニメーション実行
  return gsap.to(el, {
    ...config.to,
    duration,
    ease: "linear",
    onComplete: () => {
      // アニメーション完了後に要素を非表示にし、レイアウトに影響しないようにする
      el.style.visibility = 'hidden'; // 表示を隠す
      el.style.display = 'none'; // 完全にDOMから消す
    },
  });
};