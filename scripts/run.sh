#!/bin/bash

set -e

SESSION=ReFrameTest

if [[ `tmux ls 2>/dev/null` == *"$SESSION"* ]]; then
  tmux kill-session -t "$SESSION"
fi

FIGWHEEL='lein figwheel'
VIM='vim -S Session.vim'

tmux -2 new-session -d -s $SESSION -n figwheel
tmux send-keys "$FIGWHEEL" C-m

tmux new-window -t $SESSION:2 -n vim
tmux send-keys "$VIM" C-m

tmux select-window -t 2
tmux attach -t $SESSION
