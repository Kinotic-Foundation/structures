import{c as K}from"./Be1fzYNM.js";import{v as z,B as j,aT as G,aU as L,x as A,L as _,c as D,o as f,b as q,l as P,N as b,p as W,O as H,P as h,a as O,q as C,Q as $,aV as k}from"./DIVREvd8.js";import{s as Q}from"./CqOU29IR.js";import{s as J}from"./D5eR76zp.js";import X from"./bJxOl9-S.js";import{s as Y}from"./jS7rQ_kC.js";import"./fNKMJjk8.js";import"./BOp_CX-A.js";import"./DGB91fzl.js";var Z=z`
    .p-inputnumber {
        display: inline-flex;
        position: relative;
    }

    .p-inputnumber-button {
        display: flex;
        align-items: center;
        justify-content: center;
        flex: 0 0 auto;
        cursor: pointer;
        background: dt('inputnumber.button.background');
        color: dt('inputnumber.button.color');
        width: dt('inputnumber.button.width');
        transition:
            background dt('inputnumber.transition.duration'),
            color dt('inputnumber.transition.duration'),
            border-color dt('inputnumber.transition.duration'),
            outline-color dt('inputnumber.transition.duration');
    }

    .p-inputnumber-button:disabled {
        cursor: auto;
    }

    .p-inputnumber-button:not(:disabled):hover {
        background: dt('inputnumber.button.hover.background');
        color: dt('inputnumber.button.hover.color');
    }

    .p-inputnumber-button:not(:disabled):active {
        background: dt('inputnumber.button.active.background');
        color: dt('inputnumber.button.active.color');
    }

    .p-inputnumber-stacked .p-inputnumber-button {
        position: relative;
        border: 0 none;
    }

    .p-inputnumber-stacked .p-inputnumber-button-group {
        display: flex;
        flex-direction: column;
        position: absolute;
        inset-block-start: 1px;
        inset-inline-end: 1px;
        height: calc(100% - 2px);
        z-index: 1;
    }

    .p-inputnumber-stacked .p-inputnumber-increment-button {
        padding: 0;
        border-start-end-radius: calc(dt('inputnumber.button.border.radius') - 1px);
    }

    .p-inputnumber-stacked .p-inputnumber-decrement-button {
        padding: 0;
        border-end-end-radius: calc(dt('inputnumber.button.border.radius') - 1px);
    }

    .p-inputnumber-stacked .p-inputnumber-button {
        flex: 1 1 auto;
        border: 0 none;
    }

    .p-inputnumber-horizontal .p-inputnumber-button {
        border: 1px solid dt('inputnumber.button.border.color');
    }

    .p-inputnumber-horizontal .p-inputnumber-button:hover {
        border-color: dt('inputnumber.button.hover.border.color');
    }

    .p-inputnumber-horizontal .p-inputnumber-button:active {
        border-color: dt('inputnumber.button.active.border.color');
    }

    .p-inputnumber-horizontal .p-inputnumber-increment-button {
        order: 3;
        border-start-end-radius: dt('inputnumber.button.border.radius');
        border-end-end-radius: dt('inputnumber.button.border.radius');
        border-inline-start: 0 none;
    }

    .p-inputnumber-horizontal .p-inputnumber-input {
        order: 2;
        border-radius: 0;
    }

    .p-inputnumber-horizontal .p-inputnumber-decrement-button {
        order: 1;
        border-start-start-radius: dt('inputnumber.button.border.radius');
        border-end-start-radius: dt('inputnumber.button.border.radius');
        border-inline-end: 0 none;
    }

    .p-floatlabel:has(.p-inputnumber-horizontal) label {
        margin-inline-start: dt('inputnumber.button.width');
    }

    .p-inputnumber-vertical {
        flex-direction: column;
    }

    .p-inputnumber-vertical .p-inputnumber-button {
        border: 1px solid dt('inputnumber.button.border.color');
        padding: dt('inputnumber.button.vertical.padding');
    }

    .p-inputnumber-vertical .p-inputnumber-button:hover {
        border-color: dt('inputnumber.button.hover.border.color');
    }

    .p-inputnumber-vertical .p-inputnumber-button:active {
        border-color: dt('inputnumber.button.active.border.color');
    }

    .p-inputnumber-vertical .p-inputnumber-increment-button {
        order: 1;
        border-start-start-radius: dt('inputnumber.button.border.radius');
        border-start-end-radius: dt('inputnumber.button.border.radius');
        width: 100%;
        border-block-end: 0 none;
    }

    .p-inputnumber-vertical .p-inputnumber-input {
        order: 2;
        border-radius: 0;
        text-align: center;
    }

    .p-inputnumber-vertical .p-inputnumber-decrement-button {
        order: 3;
        border-end-start-radius: dt('inputnumber.button.border.radius');
        border-end-end-radius: dt('inputnumber.button.border.radius');
        width: 100%;
        border-block-start: 0 none;
    }

    .p-inputnumber-input {
        flex: 1 1 auto;
    }

    .p-inputnumber-fluid {
        width: 100%;
    }

    .p-inputnumber-fluid .p-inputnumber-input {
        width: 1%;
    }

    .p-inputnumber-fluid.p-inputnumber-vertical .p-inputnumber-input {
        width: 100%;
    }

    .p-inputnumber:has(.p-inputtext-sm) .p-inputnumber-button .p-icon {
        font-size: dt('form.field.sm.font.size');
        width: dt('form.field.sm.font.size');
        height: dt('form.field.sm.font.size');
    }

    .p-inputnumber:has(.p-inputtext-lg) .p-inputnumber-button .p-icon {
        font-size: dt('form.field.lg.font.size');
        width: dt('form.field.lg.font.size');
        height: dt('form.field.lg.font.size');
    }
`,ee={root:function(e){var t=e.instance,i=e.props;return["p-inputnumber p-component p-inputwrapper",{"p-invalid":t.$invalid,"p-inputwrapper-filled":t.$filled||i.allowEmpty===!1,"p-inputwrapper-focus":t.focused,"p-inputnumber-stacked":i.showButtons&&i.buttonLayout==="stacked","p-inputnumber-horizontal":i.showButtons&&i.buttonLayout==="horizontal","p-inputnumber-vertical":i.showButtons&&i.buttonLayout==="vertical","p-inputnumber-fluid":t.$fluid}]},pcInputText:"p-inputnumber-input",buttonGroup:"p-inputnumber-button-group",incrementButton:function(e){var t=e.instance,i=e.props;return["p-inputnumber-button p-inputnumber-increment-button",{"p-disabled":i.showButtons&&i.max!==null&&t.maxBoundry()}]},decrementButton:function(e){var t=e.instance,i=e.props;return["p-inputnumber-button p-inputnumber-decrement-button",{"p-disabled":i.showButtons&&i.min!==null&&t.minBoundry()}]}},te=j.extend({name:"inputnumber",style:Z,classes:ee}),ne={name:"BaseInputNumber",extends:Y,props:{format:{type:Boolean,default:!0},showButtons:{type:Boolean,default:!1},buttonLayout:{type:String,default:"stacked"},incrementButtonClass:{type:String,default:null},decrementButtonClass:{type:String,default:null},incrementButtonIcon:{type:String,default:void 0},incrementIcon:{type:String,default:void 0},decrementButtonIcon:{type:String,default:void 0},decrementIcon:{type:String,default:void 0},locale:{type:String,default:void 0},localeMatcher:{type:String,default:void 0},mode:{type:String,default:"decimal"},prefix:{type:String,default:null},suffix:{type:String,default:null},currency:{type:String,default:void 0},currencyDisplay:{type:String,default:void 0},useGrouping:{type:Boolean,default:!0},minFractionDigits:{type:Number,default:void 0},maxFractionDigits:{type:Number,default:void 0},roundingMode:{type:String,default:"halfExpand",validator:function(e){return["ceil","floor","expand","trunc","halfCeil","halfFloor","halfExpand","halfTrunc","halfEven"].includes(e)}},min:{type:Number,default:null},max:{type:Number,default:null},step:{type:Number,default:1},allowEmpty:{type:Boolean,default:!0},highlightOnFocus:{type:Boolean,default:!1},readonly:{type:Boolean,default:!1},placeholder:{type:String,default:null},inputId:{type:String,default:null},inputClass:{type:[String,Object],default:null},inputStyle:{type:Object,default:null},ariaLabelledby:{type:String,default:null},ariaLabel:{type:String,default:null},required:{type:Boolean,default:!1}},style:te,provide:function(){return{$pcInputNumber:this,$parentInstance:this}}};function x(n){"@babel/helpers - typeof";return x=typeof Symbol=="function"&&typeof Symbol.iterator=="symbol"?function(e){return typeof e}:function(e){return e&&typeof Symbol=="function"&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e},x(n)}function R(n,e){var t=Object.keys(n);if(Object.getOwnPropertySymbols){var i=Object.getOwnPropertySymbols(n);e&&(i=i.filter(function(s){return Object.getOwnPropertyDescriptor(n,s).enumerable})),t.push.apply(t,i)}return t}function U(n){for(var e=1;e<arguments.length;e++){var t=arguments[e]!=null?arguments[e]:{};e%2?R(Object(t),!0).forEach(function(i){V(n,i,t[i])}):Object.getOwnPropertyDescriptors?Object.defineProperties(n,Object.getOwnPropertyDescriptors(t)):R(Object(t)).forEach(function(i){Object.defineProperty(n,i,Object.getOwnPropertyDescriptor(t,i))})}return n}function V(n,e,t){return(e=ie(e))in n?Object.defineProperty(n,e,{value:t,enumerable:!0,configurable:!0,writable:!0}):n[e]=t,n}function ie(n){var e=re(n,"string");return x(e)=="symbol"?e:e+""}function re(n,e){if(x(n)!="object"||!n)return n;var t=n[Symbol.toPrimitive];if(t!==void 0){var i=t.call(n,e);if(x(i)!="object")return i;throw new TypeError("@@toPrimitive must return a primitive value.")}return(e==="string"?String:Number)(n)}function ue(n){return le(n)||oe(n)||ae(n)||se()}function se(){throw new TypeError(`Invalid attempt to spread non-iterable instance.
In order to be iterable, non-array objects must have a [Symbol.iterator]() method.`)}function ae(n,e){if(n){if(typeof n=="string")return F(n,e);var t={}.toString.call(n).slice(8,-1);return t==="Object"&&n.constructor&&(t=n.constructor.name),t==="Map"||t==="Set"?Array.from(n):t==="Arguments"||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(t)?F(n,e):void 0}}function oe(n){if(typeof Symbol<"u"&&n[Symbol.iterator]!=null||n["@@iterator"]!=null)return Array.from(n)}function le(n){if(Array.isArray(n))return F(n)}function F(n,e){(e==null||e>n.length)&&(e=n.length);for(var t=0,i=Array(e);t<e;t++)i[t]=n[t];return i}var ce={name:"InputNumber",extends:ne,inheritAttrs:!1,emits:["input","focus","blur"],inject:{$pcFluid:{default:null}},numberFormat:null,_numeral:null,_decimal:null,_group:null,_minusSign:null,_currency:null,_suffix:null,_prefix:null,_index:null,groupChar:"",isSpecialChar:null,prefixChar:null,suffixChar:null,timer:null,data:function(){return{d_modelValue:this.d_value,focused:!1}},watch:{d_value:function(e){this.d_modelValue=e},locale:function(e,t){this.updateConstructParser(e,t)},localeMatcher:function(e,t){this.updateConstructParser(e,t)},mode:function(e,t){this.updateConstructParser(e,t)},currency:function(e,t){this.updateConstructParser(e,t)},currencyDisplay:function(e,t){this.updateConstructParser(e,t)},useGrouping:function(e,t){this.updateConstructParser(e,t)},minFractionDigits:function(e,t){this.updateConstructParser(e,t)},maxFractionDigits:function(e,t){this.updateConstructParser(e,t)},suffix:function(e,t){this.updateConstructParser(e,t)},prefix:function(e,t){this.updateConstructParser(e,t)}},created:function(){this.constructParser()},methods:{getOptions:function(){return{localeMatcher:this.localeMatcher,style:this.mode,currency:this.currency,currencyDisplay:this.currencyDisplay,useGrouping:this.useGrouping,minimumFractionDigits:this.minFractionDigits,maximumFractionDigits:this.maxFractionDigits,roundingMode:this.roundingMode}},constructParser:function(){this.numberFormat=new Intl.NumberFormat(this.locale,this.getOptions());var e=ue(new Intl.NumberFormat(this.locale,{useGrouping:!1}).format(9876543210)).reverse(),t=new Map(e.map(function(i,s){return[i,s]}));this._numeral=new RegExp("[".concat(e.join(""),"]"),"g"),this._group=this.getGroupingExpression(),this._minusSign=this.getMinusSignExpression(),this._currency=this.getCurrencyExpression(),this._decimal=this.getDecimalExpression(),this._suffix=this.getSuffixExpression(),this._prefix=this.getPrefixExpression(),this._index=function(i){return t.get(i)}},updateConstructParser:function(e,t){e!==t&&this.constructParser()},escapeRegExp:function(e){return e.replace(/[-[\]{}()*+?.,\\^$|#\s]/g,"\\$&")},getDecimalExpression:function(){var e=new Intl.NumberFormat(this.locale,U(U({},this.getOptions()),{},{useGrouping:!1}));return new RegExp("[".concat(e.format(1.1).replace(this._currency,"").trim().replace(this._numeral,""),"]"),"g")},getGroupingExpression:function(){var e=new Intl.NumberFormat(this.locale,{useGrouping:!0});return this.groupChar=e.format(1e6).trim().replace(this._numeral,"").charAt(0),new RegExp("[".concat(this.groupChar,"]"),"g")},getMinusSignExpression:function(){var e=new Intl.NumberFormat(this.locale,{useGrouping:!1});return new RegExp("[".concat(e.format(-1).trim().replace(this._numeral,""),"]"),"g")},getCurrencyExpression:function(){if(this.currency){var e=new Intl.NumberFormat(this.locale,{style:"currency",currency:this.currency,currencyDisplay:this.currencyDisplay,minimumFractionDigits:0,maximumFractionDigits:0,roundingMode:this.roundingMode});return new RegExp("[".concat(e.format(1).replace(/\s/g,"").replace(this._numeral,"").replace(this._group,""),"]"),"g")}return new RegExp("[]","g")},getPrefixExpression:function(){if(this.prefix)this.prefixChar=this.prefix;else{var e=new Intl.NumberFormat(this.locale,{style:this.mode,currency:this.currency,currencyDisplay:this.currencyDisplay});this.prefixChar=e.format(1).split("1")[0]}return new RegExp("".concat(this.escapeRegExp(this.prefixChar||"")),"g")},getSuffixExpression:function(){if(this.suffix)this.suffixChar=this.suffix;else{var e=new Intl.NumberFormat(this.locale,{style:this.mode,currency:this.currency,currencyDisplay:this.currencyDisplay,minimumFractionDigits:0,maximumFractionDigits:0,roundingMode:this.roundingMode});this.suffixChar=e.format(1).split("1")[1]}return new RegExp("".concat(this.escapeRegExp(this.suffixChar||"")),"g")},formatValue:function(e){if(e!=null){if(e==="-")return e;if(this.format){var t=new Intl.NumberFormat(this.locale,this.getOptions()),i=t.format(e);return this.prefix&&(i=this.prefix+i),this.suffix&&(i=i+this.suffix),i}return e.toString()}return""},parseValue:function(e){var t=e.replace(this._suffix,"").replace(this._prefix,"").trim().replace(/\s/g,"").replace(this._currency,"").replace(this._group,"").replace(this._minusSign,"-").replace(this._decimal,".").replace(this._numeral,this._index);if(t){if(t==="-")return t;var i=+t;return isNaN(i)?null:i}return null},repeat:function(e,t,i){var s=this;if(!this.readonly){var r=t||500;this.clearTimer(),this.timer=setTimeout(function(){s.repeat(e,40,i)},r),this.spin(e,i)}},spin:function(e,t){if(this.$refs.input){var i=this.step*t,s=this.parseValue(this.$refs.input.$el.value)||0,r=this.validateValue(s+i);this.updateInput(r,null,"spin"),this.updateModel(e,r),this.handleOnInput(e,s,r)}},onUpButtonMouseDown:function(e){this.disabled||(this.$refs.input.$el.focus(),this.repeat(e,null,1),e.preventDefault())},onUpButtonMouseUp:function(){this.disabled||this.clearTimer()},onUpButtonMouseLeave:function(){this.disabled||this.clearTimer()},onUpButtonKeyUp:function(){this.disabled||this.clearTimer()},onUpButtonKeyDown:function(e){(e.code==="Space"||e.code==="Enter"||e.code==="NumpadEnter")&&this.repeat(e,null,1)},onDownButtonMouseDown:function(e){this.disabled||(this.$refs.input.$el.focus(),this.repeat(e,null,-1),e.preventDefault())},onDownButtonMouseUp:function(){this.disabled||this.clearTimer()},onDownButtonMouseLeave:function(){this.disabled||this.clearTimer()},onDownButtonKeyUp:function(){this.disabled||this.clearTimer()},onDownButtonKeyDown:function(e){(e.code==="Space"||e.code==="Enter"||e.code==="NumpadEnter")&&this.repeat(e,null,-1)},onUserInput:function(){this.isSpecialChar&&(this.$refs.input.$el.value=this.lastValue),this.isSpecialChar=!1},onInputKeyDown:function(e){if(!this.readonly){if(e.altKey||e.ctrlKey||e.metaKey){this.isSpecialChar=!0,this.lastValue=this.$refs.input.$el.value;return}this.lastValue=e.target.value;var t=e.target.selectionStart,i=e.target.selectionEnd,s=i-t,r=e.target.value,u=null,a=e.code||e.key;switch(a){case"ArrowUp":this.spin(e,1),e.preventDefault();break;case"ArrowDown":this.spin(e,-1),e.preventDefault();break;case"ArrowLeft":if(s>1){var m=this.isNumeralChar(r.charAt(t))?t+1:t+2;this.$refs.input.$el.setSelectionRange(m,m)}else this.isNumeralChar(r.charAt(t-1))||e.preventDefault();break;case"ArrowRight":if(s>1){var c=i-1;this.$refs.input.$el.setSelectionRange(c,c)}else this.isNumeralChar(r.charAt(t))||e.preventDefault();break;case"Tab":case"Enter":case"NumpadEnter":u=this.validateValue(this.parseValue(r)),this.$refs.input.$el.value=this.formatValue(u),this.$refs.input.$el.setAttribute("aria-valuenow",u),this.updateModel(e,u);break;case"Backspace":{if(e.preventDefault(),t===i){var g=r.charAt(t-1),o=this.getDecimalCharIndexes(r),p=o.decimalCharIndex,l=o.decimalCharIndexWithoutPrefix;if(this.isNumeralChar(g)){var v=this.getDecimalLength(r);if(this._group.test(g))this._group.lastIndex=0,u=r.slice(0,t-2)+r.slice(t-1);else if(this._decimal.test(g))this._decimal.lastIndex=0,v?this.$refs.input.$el.setSelectionRange(t-1,t-1):u=r.slice(0,t-1)+r.slice(t);else if(p>0&&t>p){var I=this.isDecimalMode()&&(this.minFractionDigits||0)<v?"":"0";u=r.slice(0,t-1)+I+r.slice(t)}else l===1?(u=r.slice(0,t-1)+"0"+r.slice(t),u=this.parseValue(u)>0?u:""):u=r.slice(0,t-1)+r.slice(t)}this.updateValue(e,u,null,"delete-single")}else u=this.deleteRange(r,t,i),this.updateValue(e,u,null,"delete-range");break}case"Delete":if(e.preventDefault(),t===i){var d=r.charAt(t),y=this.getDecimalCharIndexes(r),w=y.decimalCharIndex,M=y.decimalCharIndexWithoutPrefix;if(this.isNumeralChar(d)){var B=this.getDecimalLength(r);if(this._group.test(d))this._group.lastIndex=0,u=r.slice(0,t)+r.slice(t+2);else if(this._decimal.test(d))this._decimal.lastIndex=0,B?this.$refs.input.$el.setSelectionRange(t+1,t+1):u=r.slice(0,t)+r.slice(t+1);else if(w>0&&t>w){var S=this.isDecimalMode()&&(this.minFractionDigits||0)<B?"":"0";u=r.slice(0,t)+S+r.slice(t+1)}else M===1?(u=r.slice(0,t)+"0"+r.slice(t+1),u=this.parseValue(u)>0?u:""):u=r.slice(0,t)+r.slice(t+1)}this.updateValue(e,u,null,"delete-back-single")}else u=this.deleteRange(r,t,i),this.updateValue(e,u,null,"delete-range");break;case"Home":e.preventDefault(),A(this.min)&&this.updateModel(e,this.min);break;case"End":e.preventDefault(),A(this.max)&&this.updateModel(e,this.max);break}}},onInputKeyPress:function(e){if(!this.readonly){var t=e.key,i=this.isDecimalSign(t),s=this.isMinusSign(t);e.code!=="Enter"&&e.preventDefault(),(Number(t)>=0&&Number(t)<=9||s||i)&&this.insert(e,t,{isDecimalSign:i,isMinusSign:s})}},onPaste:function(e){e.preventDefault();var t=(e.clipboardData||window.clipboardData).getData("Text");if(t){var i=this.parseValue(t);i!=null&&this.insert(e,i.toString())}},allowMinusSign:function(){return this.min===null||this.min<0},isMinusSign:function(e){return this._minusSign.test(e)||e==="-"?(this._minusSign.lastIndex=0,!0):!1},isDecimalSign:function(e){var t;return(t=this.locale)!==null&&t!==void 0&&t.includes("fr")&&[".",","].includes(e)||this._decimal.test(e)?(this._decimal.lastIndex=0,!0):!1},isDecimalMode:function(){return this.mode==="decimal"},getDecimalCharIndexes:function(e){var t=e.search(this._decimal);this._decimal.lastIndex=0;var i=e.replace(this._prefix,"").trim().replace(/\s/g,"").replace(this._currency,""),s=i.search(this._decimal);return this._decimal.lastIndex=0,{decimalCharIndex:t,decimalCharIndexWithoutPrefix:s}},getCharIndexes:function(e){var t=e.search(this._decimal);this._decimal.lastIndex=0;var i=e.search(this._minusSign);this._minusSign.lastIndex=0;var s=e.search(this._suffix);this._suffix.lastIndex=0;var r=e.search(this._currency);return this._currency.lastIndex=0,{decimalCharIndex:t,minusCharIndex:i,suffixCharIndex:s,currencyCharIndex:r}},insert:function(e,t){var i=arguments.length>2&&arguments[2]!==void 0?arguments[2]:{isDecimalSign:!1,isMinusSign:!1},s=t.search(this._minusSign);if(this._minusSign.lastIndex=0,!(!this.allowMinusSign()&&s!==-1)){var r=this.$refs.input.$el.selectionStart,u=this.$refs.input.$el.selectionEnd,a=this.$refs.input.$el.value.trim(),m=this.getCharIndexes(a),c=m.decimalCharIndex,g=m.minusCharIndex,o=m.suffixCharIndex,p=m.currencyCharIndex,l;if(i.isMinusSign){var v=g===-1;(r===0||r===p+1)&&(l=a,(v||u!==0)&&(l=this.insertText(a,t,0,u)),this.updateValue(e,l,t,"insert"))}else if(i.isDecimalSign)c>0&&r===c?this.updateValue(e,a,t,"insert"):c>r&&c<u?(l=this.insertText(a,t,r,u),this.updateValue(e,l,t,"insert")):c===-1&&this.maxFractionDigits&&(l=this.insertText(a,t,r,u),this.updateValue(e,l,t,"insert"));else{var I=this.numberFormat.resolvedOptions().maximumFractionDigits,d=r!==u?"range-insert":"insert";if(c>0&&r>c){if(r+t.length-(c+1)<=I){var y=p>=r?p-1:o>=r?o:a.length;l=a.slice(0,r)+t+a.slice(r+t.length,y)+a.slice(y),this.updateValue(e,l,t,d)}}else l=this.insertText(a,t,r,u),this.updateValue(e,l,t,d)}}},insertText:function(e,t,i,s){var r=t==="."?t:t.split(".");if(r.length===2){var u=e.slice(i,s).search(this._decimal);return this._decimal.lastIndex=0,u>0?e.slice(0,i)+this.formatValue(t)+e.slice(s):this.formatValue(t)||e}else return s-i===e.length?this.formatValue(t):i===0?t+e.slice(s):s===e.length?e.slice(0,i)+t:e.slice(0,i)+t+e.slice(s)},deleteRange:function(e,t,i){var s;return i-t===e.length?s="":t===0?s=e.slice(i):i===e.length?s=e.slice(0,t):s=e.slice(0,t)+e.slice(i),s},initCursor:function(){var e=this.$refs.input.$el.selectionStart,t=this.$refs.input.$el.value,i=t.length,s=null,r=(this.prefixChar||"").length;t=t.replace(this._prefix,""),e=e-r;var u=t.charAt(e);if(this.isNumeralChar(u))return e+r;for(var a=e-1;a>=0;)if(u=t.charAt(a),this.isNumeralChar(u)){s=a+r;break}else a--;if(s!==null)this.$refs.input.$el.setSelectionRange(s+1,s+1);else{for(a=e;a<i;)if(u=t.charAt(a),this.isNumeralChar(u)){s=a+r;break}else a++;s!==null&&this.$refs.input.$el.setSelectionRange(s,s)}return s||0},onInputClick:function(){var e=this.$refs.input.$el.value;!this.readonly&&e!==L()&&this.initCursor()},isNumeralChar:function(e){return e.length===1&&(this._numeral.test(e)||this._decimal.test(e)||this._group.test(e)||this._minusSign.test(e))?(this.resetRegex(),!0):!1},resetRegex:function(){this._numeral.lastIndex=0,this._decimal.lastIndex=0,this._group.lastIndex=0,this._minusSign.lastIndex=0},updateValue:function(e,t,i,s){var r=this.$refs.input.$el.value,u=null;t!=null&&(u=this.parseValue(t),u=!u&&!this.allowEmpty?this.min||0:u,this.updateInput(u,i,s,t),this.handleOnInput(e,r,u))},handleOnInput:function(e,t,i){if(this.isValueChanged(t,i)){var s,r;this.$emit("input",{originalEvent:e,value:i,formattedValue:t}),(s=(r=this.formField).onInput)===null||s===void 0||s.call(r,{originalEvent:e,value:i})}},isValueChanged:function(e,t){if(t===null&&e!==null)return!0;if(t!=null){var i=typeof e=="string"?this.parseValue(e):e;return t!==i}return!1},validateValue:function(e){return e==="-"||e==null?null:this.min!=null&&e<this.min?this.min:this.max!=null&&e>this.max?this.max:e},updateInput:function(e,t,i,s){t=t||"";var r=this.$refs.input.$el.value,u=this.formatValue(e),a=r.length;if(u!==s&&(u=this.concatValues(u,s)),a===0){this.$refs.input.$el.value=u,this.$refs.input.$el.setSelectionRange(0,0);var m=this.initCursor(),c=m+t.length;this.$refs.input.$el.setSelectionRange(c,c)}else{var g=this.$refs.input.$el.selectionStart,o=this.$refs.input.$el.selectionEnd;this.$refs.input.$el.value=u;var p=u.length;if(i==="range-insert"){var l=this.parseValue((r||"").slice(0,g)),v=l!==null?l.toString():"",I=v.split("").join("(".concat(this.groupChar,")?")),d=new RegExp(I,"g");d.test(u);var y=t.split("").join("(".concat(this.groupChar,")?")),w=new RegExp(y,"g");w.test(u.slice(d.lastIndex)),o=d.lastIndex+w.lastIndex,this.$refs.input.$el.setSelectionRange(o,o)}else if(p===a)i==="insert"||i==="delete-back-single"?this.$refs.input.$el.setSelectionRange(o+1,o+1):i==="delete-single"?this.$refs.input.$el.setSelectionRange(o-1,o-1):(i==="delete-range"||i==="spin")&&this.$refs.input.$el.setSelectionRange(o,o);else if(i==="delete-back-single"){var M=r.charAt(o-1),B=r.charAt(o),S=a-p,N=this._group.test(B);N&&S===1?o+=1:!N&&this.isNumeralChar(M)&&(o+=-1*S+1),this._group.lastIndex=0,this.$refs.input.$el.setSelectionRange(o,o)}else if(r==="-"&&i==="insert"){this.$refs.input.$el.setSelectionRange(0,0);var T=this.initCursor(),E=T+t.length+1;this.$refs.input.$el.setSelectionRange(E,E)}else o=o+(p-a),this.$refs.input.$el.setSelectionRange(o,o)}this.$refs.input.$el.setAttribute("aria-valuenow",e)},concatValues:function(e,t){if(e&&t){var i=t.search(this._decimal);return this._decimal.lastIndex=0,this.suffixChar?i!==-1?e.replace(this.suffixChar,"").split(this._decimal)[0]+t.replace(this.suffixChar,"").slice(i)+this.suffixChar:e:i!==-1?e.split(this._decimal)[0]+t.slice(i):e}return e},getDecimalLength:function(e){if(e){var t=e.split(this._decimal);if(t.length===2)return t[1].replace(this._suffix,"").trim().replace(/\s/g,"").replace(this._currency,"").length}return 0},updateModel:function(e,t){this.writeValue(t,e)},onInputFocus:function(e){this.focused=!0,!this.disabled&&!this.readonly&&this.$refs.input.$el.value!==L()&&this.highlightOnFocus&&e.target.select(),this.$emit("focus",e)},onInputBlur:function(e){var t,i;this.focused=!1;var s=e.target,r=this.validateValue(this.parseValue(s.value));this.$emit("blur",{originalEvent:e,value:s.value}),(t=(i=this.formField).onBlur)===null||t===void 0||t.call(i,e),s.value=this.formatValue(r),s.setAttribute("aria-valuenow",r),this.updateModel(e,r),!this.disabled&&!this.readonly&&this.highlightOnFocus&&G()},clearTimer:function(){this.timer&&clearTimeout(this.timer)},maxBoundry:function(){return this.d_value>=this.max},minBoundry:function(){return this.d_value<=this.min}},computed:{upButtonListeners:function(){var e=this;return{mousedown:function(i){return e.onUpButtonMouseDown(i)},mouseup:function(i){return e.onUpButtonMouseUp(i)},mouseleave:function(i){return e.onUpButtonMouseLeave(i)},keydown:function(i){return e.onUpButtonKeyDown(i)},keyup:function(i){return e.onUpButtonKeyUp(i)}}},downButtonListeners:function(){var e=this;return{mousedown:function(i){return e.onDownButtonMouseDown(i)},mouseup:function(i){return e.onDownButtonMouseUp(i)},mouseleave:function(i){return e.onDownButtonMouseLeave(i)},keydown:function(i){return e.onDownButtonKeyDown(i)},keyup:function(i){return e.onDownButtonKeyUp(i)}}},formattedValue:function(){var e=!this.d_value&&!this.allowEmpty?0:this.d_value;return this.formatValue(e)},getFormatter:function(){return this.numberFormat},dataP:function(){return K(V(V({invalid:this.$invalid,fluid:this.$fluid,filled:this.$variant==="filled"},this.size,this.size),this.buttonLayout,this.showButtons&&this.buttonLayout))}},components:{InputText:X,AngleUpIcon:J,AngleDownIcon:Q}},pe=["data-p"],de=["data-p"],he=["disabled","data-p"],me=["disabled","data-p"],fe=["disabled","data-p"],be=["disabled","data-p"];function ge(n,e,t,i,s,r){var u=_("InputText");return f(),D("span",h({class:n.cx("root")},n.ptmi("root"),{"data-p":r.dataP}),[q(u,{ref:"input",id:n.inputId,name:n.$formName,role:"spinbutton",class:H([n.cx("pcInputText"),n.inputClass]),style:W(n.inputStyle),defaultValue:r.formattedValue,"aria-valuemin":n.min,"aria-valuemax":n.max,"aria-valuenow":n.d_value,inputmode:n.mode==="decimal"&&!n.minFractionDigits?"numeric":"decimal",disabled:n.disabled,readonly:n.readonly,placeholder:n.placeholder,"aria-labelledby":n.ariaLabelledby,"aria-label":n.ariaLabel,required:n.required,size:n.size,invalid:n.invalid,variant:n.variant,onInput:r.onUserInput,onKeydown:r.onInputKeyDown,onKeypress:r.onInputKeyPress,onPaste:r.onPaste,onClick:r.onInputClick,onFocus:r.onInputFocus,onBlur:r.onInputBlur,pt:n.ptm("pcInputText"),unstyled:n.unstyled,"data-p":r.dataP},null,8,["id","name","class","style","defaultValue","aria-valuemin","aria-valuemax","aria-valuenow","inputmode","disabled","readonly","placeholder","aria-labelledby","aria-label","required","size","invalid","variant","onInput","onKeydown","onKeypress","onPaste","onClick","onFocus","onBlur","pt","unstyled","data-p"]),n.showButtons&&n.buttonLayout==="stacked"?(f(),D("span",h({key:0,class:n.cx("buttonGroup")},n.ptm("buttonGroup"),{"data-p":r.dataP}),[b(n.$slots,"incrementbutton",{listeners:r.upButtonListeners},function(){return[O("button",h({class:[n.cx("incrementButton"),n.incrementButtonClass]},k(r.upButtonListeners),{disabled:n.disabled,tabindex:-1,"aria-hidden":"true",type:"button"},n.ptm("incrementButton"),{"data-p":r.dataP}),[b(n.$slots,n.$slots.incrementicon?"incrementicon":"incrementbuttonicon",{},function(){return[(f(),C($(n.incrementIcon||n.incrementButtonIcon?"span":"AngleUpIcon"),h({class:[n.incrementIcon,n.incrementButtonIcon]},n.ptm("incrementIcon"),{"data-pc-section":"incrementicon"}),null,16,["class"]))]})],16,he)]}),b(n.$slots,"decrementbutton",{listeners:r.downButtonListeners},function(){return[O("button",h({class:[n.cx("decrementButton"),n.decrementButtonClass]},k(r.downButtonListeners),{disabled:n.disabled,tabindex:-1,"aria-hidden":"true",type:"button"},n.ptm("decrementButton"),{"data-p":r.dataP}),[b(n.$slots,n.$slots.decrementicon?"decrementicon":"decrementbuttonicon",{},function(){return[(f(),C($(n.decrementIcon||n.decrementButtonIcon?"span":"AngleDownIcon"),h({class:[n.decrementIcon,n.decrementButtonIcon]},n.ptm("decrementIcon"),{"data-pc-section":"decrementicon"}),null,16,["class"]))]})],16,me)]})],16,de)):P("",!0),b(n.$slots,"incrementbutton",{listeners:r.upButtonListeners},function(){return[n.showButtons&&n.buttonLayout!=="stacked"?(f(),D("button",h({key:0,class:[n.cx("incrementButton"),n.incrementButtonClass]},k(r.upButtonListeners),{disabled:n.disabled,tabindex:-1,"aria-hidden":"true",type:"button"},n.ptm("incrementButton"),{"data-p":r.dataP}),[b(n.$slots,n.$slots.incrementicon?"incrementicon":"incrementbuttonicon",{},function(){return[(f(),C($(n.incrementIcon||n.incrementButtonIcon?"span":"AngleUpIcon"),h({class:[n.incrementIcon,n.incrementButtonIcon]},n.ptm("incrementIcon"),{"data-pc-section":"incrementicon"}),null,16,["class"]))]})],16,fe)):P("",!0)]}),b(n.$slots,"decrementbutton",{listeners:r.downButtonListeners},function(){return[n.showButtons&&n.buttonLayout!=="stacked"?(f(),D("button",h({key:0,class:[n.cx("decrementButton"),n.decrementButtonClass]},k(r.downButtonListeners),{disabled:n.disabled,tabindex:-1,"aria-hidden":"true",type:"button"},n.ptm("decrementButton"),{"data-p":r.dataP}),[b(n.$slots,n.$slots.decrementicon?"decrementicon":"decrementbuttonicon",{},function(){return[(f(),C($(n.decrementIcon||n.decrementButtonIcon?"span":"AngleDownIcon"),h({class:[n.decrementIcon,n.decrementButtonIcon]},n.ptm("decrementIcon"),{"data-pc-section":"decrementicon"}),null,16,["class"]))]})],16,be)):P("",!0)]})],16,pe)}ce.render=ge;export{ce as default};
